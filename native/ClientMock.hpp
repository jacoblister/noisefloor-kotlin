#include "Process.hpp"

class ClientMock {
  public:
    ClientMock(Process &process) : process(process) { }
    void init();
    void run();
  private:
    Process &process;
};
