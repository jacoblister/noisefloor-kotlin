#pragma once

#include <string>

class Process {
  public:
    Process() {}
    virtual void init() {};
    virtual void start(int sampling_rate, int samples_per_frame) {};
    virtual float *process(float *samples) {};
    //    virtual void process(float **samples_in, float **samples_out, int** midi_in, int **midi_out); //maybe?
    virtual void stop() {};
    virtual std::string query(std::string endpoint, std::string request) {};
};