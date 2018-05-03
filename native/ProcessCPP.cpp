#include "ProcessCPP.hpp"

#include <iostream>

result<bool> ProcessCPP::init(void) {
    return true;
}

result<bool> ProcessCPP::start(int samplingRate, int samplesPerFrame) {
    this->samplesPerFrame = samplesPerFrame;
    this->oscillator.start(samplingRate);

    return true;
}

result<bool> ProcessCPP::process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut) {
//    for (int i = 0; i < this->samplesPerFrame; i++) {
//        samplesOut[0][i] = samplesIn[0][i];
//        samplesOut[1][i] = samplesIn[1][i];
//    }

    for (int i = 0; i < this->samplesPerFrame; i++) {
        float sample = this->oscillator.process();
        samplesOut[0][i] = sample;
        samplesOut[1][i] = sample;
    }

    return true;
}

result<bool> ProcessCPP::stop(void) {
    return true;
}

std::string ProcessCPP::query(std::string endpoint, std::string request) { return ""; }
