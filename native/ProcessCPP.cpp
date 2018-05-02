#include "ProcessCPP.hpp"

#include <iostream>

void ProcessCPP::init(void) {

}

void ProcessCPP::start(int sampling_rate, int samples_per_frame) {
    this->samplesPerFrame = samples_per_frame;
    this->oscillator.start(sampling_rate);
}

void ProcessCPP::process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut) {
//    for (int i = 0; i < this->samplesPerFrame; i++) {
//        samplesOut[0][i] = samplesIn[0][i];
//        samplesOut[1][i] = samplesIn[1][i];
//    }

    for (int i = 0; i < this->samplesPerFrame; i++) {
        float sample = this->oscillator.process();
        samplesOut[0][i] = sample;
        samplesOut[1][i] = sample;
    }
}

void ProcessCPP::stop(void) {}

std::string ProcessCPP::query(std::string endpoint, std::string request) { return ""; }
