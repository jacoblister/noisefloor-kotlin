#pragma once

#include <string>
#include <vector>

struct MIDIEvent {
    int time;
    int length;
    char *data;
};

class Process {
  public:
    Process() {}
    virtual void init() {};
    virtual void start(int sampling_rate, int samples_per_frame) {};
    virtual void process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut) {};
    virtual void stop() {};
    virtual std::string query(std::string endpoint, std::string request) { return ""; };
};