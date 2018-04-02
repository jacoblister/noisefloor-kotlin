#pragma once

#include <string>
#include <vector>

class Process {
  public:
    Process() {}
    virtual void init() {};
    virtual void start(int sampling_rate, int samples_per_frame) {};
    virtual void process(float *samples0, float *samples1) {};
    virtual void stop() {};
    virtual std::string query(std::string endpoint, std::string request) {};
};