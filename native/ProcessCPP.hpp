#include "Process.hpp"
#include "Processor/Oscillator.hpp"

class ProcessCPP: public Process {
  private:
    int samplesPerFrame;
    Oscillator oscillator;
  public:
    ProcessCPP() : oscillator() {}
    virtual void init(void);
    virtual void start(int sampling_rate, int samples_per_frame);
    virtual void process(std::vector<float *> samplesIn, std::vector<float *> samplesOut, std::vector<MIDIEvent> midiIn, std::vector<MIDIEvent> midiOut);
    virtual void stop(void);
    virtual std::string query(std::string endpoint, std::string request);
};
