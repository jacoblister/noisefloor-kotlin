#include "DriverASIO.hpp"

#include <iostream>
#include <thread>

#define ASIO_DRIVER_NAME "ASIO4ALL v2"
//#define ASIO_DRIVER_NAME "GP-10"

// Driver object, no callback parameter exists for this
static DriverASIO *driver;

void ASIO_bufferSwitch(long index, ASIOBool processNow) {
    std::cout << "ASIO_bufferSwitch" << std::endl;

    if (driver->getPostOutput()) {
        ASIOOutputReady();
    }
}

void ASIO_sampleRateChanged(ASIOSampleRate sRate) {
    std::cout << "ASIO_sampleRateChanged" << std::endl;
}

long ASIO_asioMessages(long selector, long value, void* message, double* opt) {
    std::cout << "ASIO_asioMessages" << std::endl;

    return 0;
}

ASIOTime *ASIO_bufferSwitchTimeInfo(ASIOTime *timeInfo, long index, ASIOBool processNow) {
    std::cout << "ASIO_bufferSwitchTimeInfo" << std::endl;

    return 0;
}

void DriverASIO::init() {
}

bool DriverASIO::start() {
	ASIOError result;

    // Basic Init
    driver = this;
    if (!this->asioDrivers.loadDriver(ASIO_DRIVER_NAME)) { return false; }
    if (ASIOInit(&this->driverInfo) != ASE_OK)     { return false; }

//    ASIOControlPanel();

    // Get Driver Info
    if (ASIOGetChannels(&this->inputChannels, &this->outputChannels) != ASE_OK)                                { return false; }
    if (ASIOGetBufferSize(&this->minSize, &this->maxSize, &this->preferredSize, &this->granularity) != ASE_OK) { return false; }
    if (ASIOGetSampleRate(&this->sampleRate) != ASE_OK)                                                        { return false; }
    this->postOutput = (ASIOOutputReady() == ASE_OK);
    std::cout << "Post Output: " << this->postOutput << std::endl;
    std::cout << "Preferred Size: " << this->preferredSize << std::endl;

    // Allocate Buffers
    ASIOBufferInfo *info = this->bufferInfos;
    for(int i = 0; i < this->inputChannels; i++, info++) {
        info->isInput = ASIOTrue;
        info->channelNum = i;
        info->buffers[0] = info->buffers[1] = 0;
    }
	for(int i = 0; i < this->outputChannels; i++, info++) {
		info->isInput = ASIOFalse;
		info->channelNum = i;
		info->buffers[0] = info->buffers[1] = 0;
	}
    this->asioCallbacks.bufferSwitch         = &ASIO_bufferSwitch;
    this->asioCallbacks.sampleRateDidChange  = &ASIO_sampleRateChanged;
    this->asioCallbacks.asioMessage          = &ASIO_asioMessages;
    this->asioCallbacks.bufferSwitchTimeInfo = &ASIO_bufferSwitchTimeInfo;
	result = ASIOCreateBuffers(this->bufferInfos,
		this->inputChannels + this->outputChannels,
		this->preferredSize, &this->asioCallbacks);
    std::cout << "Allocating Buffers: " << result << std::endl;
    if (result != ASE_OK)                          { return false; }

    // Start Audio
    if (ASIOStart() != ASE_OK)                     { return false; }

    return true;
}

bool DriverASIO::stop() {
    std::cout << "ASIO Stop " << std::endl;
    ASIOStop();
    std::cout << "ASIO ASIODisposeBuffers " << std::endl;
    ASIODisposeBuffers();
//    std::cout << "ASIO ASIOExit " << std::endl;
//    ASIOExit();

    this->asioDrivers.removeCurrentDriver();

    return true;
}
