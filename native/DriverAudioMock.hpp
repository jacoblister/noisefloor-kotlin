#include "Process.hpp"
#include "DriverMidi.hpp"

#include <thread>
#include <atomic>

class DriverAudioMock {
  public:
    DriverAudioMock(Process& process) : process(process) { }
    result<bool> init();
    result<bool> start();
    result<bool> stop();

    inline void setMidiDriver(DriverMidi *driverMidi)  { }

    inline Process& getProcess(void) { return process; }
    inline bool getStopRequest(void) { return stopRequest; }
  private:
    std::thread thread;
    std::atomic<bool> stopRequest;
    Process& process;
};
