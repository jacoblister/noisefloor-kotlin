#include "Process.hpp"

class ApiRESTServer {
  public:
    ApiRESTServer(Process &process) : process(process) { }
    void init();
    void run();
  private:
    Process &process;
};
