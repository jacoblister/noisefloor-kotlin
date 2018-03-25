#include "Process.hpp"

#include <jack/jack.h>

class DriverJack {
  public:
    DriverJack(Process& process) : process(process) { }
    void init();
    void start();

    inline Process&       getProcess(void)        { return process; }
    inline jack_client_t* getJackClient(void)     { return jack_client; }
    inline jack_port_t*   getJackInputPort(void)  { return jack_input_port; }
    inline jack_port_t*   getJackOutputPort(void) { return jack_output_port; }
  private:
    Process& process;

    jack_client_t* jack_client;
    jack_port_t* jack_input_port;
    jack_port_t* jack_output_port;
};
