#include "Process.hpp"

class ClientMock {
  public:
    ClientMock(Process &process) : process(process) { }
    void init();
    bool start();
    bool stop();
  private:
    Process &process;
};
