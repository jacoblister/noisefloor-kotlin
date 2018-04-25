#include "ProcessMock.hpp"

#include <iostream>

void ProcessMock::init(void) {
    std::cout << "init process mock" << std::endl;
}

void ProcessMock::start(int sampling_rate, int samples_per_frame) {}
void ProcessMock::process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut) {}
void ProcessMock::stop(void) {}

std::string ProcessMock::query(std::string endpoint, std::string request) {
    return "";
}
