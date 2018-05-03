#include "DriverMock.hpp"

#include <iostream>

const int SAMPLE_RATE       = 44100;
const int SAMPLES_PER_FRAME = 256;

void process_thread(DriverMock *driver) {
    static bool is_init = 0;
    if (!is_init) {
        driver->getProcess().init();
        driver->getProcess().start(SAMPLE_RATE, SAMPLES_PER_FRAME);
        is_init = 1;
    }

    float buffer[SAMPLES_PER_FRAME];

    std::vector<float *> samplesIn = { buffer, buffer };
    std::vector<MIDIEvent> midiIn(0);

    while (!driver->getStopRequest()) {
        std::cout << "mock driver run" << std::endl;
        driver->getProcess().process(samplesIn, samplesIn, midiIn, midiIn);

        std::this_thread::sleep_for(std::chrono::seconds(1));
    }
}

result<bool> DriverMock::init() {
    return true;
}

result<bool> DriverMock::start() {
    this->stopRequest = false;
    this->thread = std::thread(process_thread, this);

    return true;
}

result<bool> DriverMock::stop() {
    this->stopRequest = true;
    this->thread.join();

    return true;
}