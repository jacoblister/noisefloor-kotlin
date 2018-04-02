#include "DriverMock.hpp"

#include <unistd.h>

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

    while (true) {
        driver->getProcess().process(buffer, buffer);

        usleep(1000000);
    }
}

void DriverMock::init() {
}

void DriverMock::start() {
    this->thread = std::thread(process_thread, this);
}