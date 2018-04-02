#include "Process.hpp"

#include <thread>

class DriverMock {
  public:
    DriverMock(Process& process) : process(process) { }
    void init();
    void start();

    inline Process& getProcess(void) { return process; }
  private:
    std::thread thread;
    Process& process;
};
