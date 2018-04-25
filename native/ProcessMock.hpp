#include "Process.hpp"

class ProcessMock: public Process {
  public:
    ProcessMock() {}
    virtual void init(void);
    virtual void start(int sampling_rate, int samples_per_frame);
    virtual void process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut);
    virtual void stop(void);
    virtual std::string query(std::string endpoint, std::string request);
};
