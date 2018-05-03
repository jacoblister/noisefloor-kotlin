#include "Process.hpp"

#include <thread>
#include <atomic>

class DriverMock {
  public:
    DriverMock(Process& process) : process(process) { }
    result<bool> init();
    result<bool> start();
    result<bool> stop();

    inline Process& getProcess(void) { return process; }
    inline bool getStopRequest(void) { return stopRequest; }
  private:
    std::thread thread;
    std::atomic<bool> stopRequest;
    Process& process;
};
