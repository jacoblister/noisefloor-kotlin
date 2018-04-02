#include "Process.hpp"

#include <jack/jack.h>

class DriverJack {
  public:
    DriverJack(Process& process) : process(process) { }
    void init();
    void start();

    inline Process&       getProcess(void)         { return process; }
    inline jack_client_t* getJackClient(void)      { return jack_client; }
    inline jack_port_t*   getJackInputPort(int i)  { return jack_input_port[i]; }
    inline jack_port_t*   getJackOutputPort(int i) { return jack_output_port[i]; }
  private:
    Process& process;

    jack_client_t* jack_client;
    jack_port_t* jack_input_port[2];
    jack_port_t* jack_output_port[2];
};
