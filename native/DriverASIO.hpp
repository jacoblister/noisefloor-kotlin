#include "Process.hpp"

#define WIN32 1
#include "asio/asiosys.h"		// platform definition
#include "asio/asio.h"
#include "asio/asiodrivers.h"


class DriverASIO {
  public:
    DriverASIO(Process& process) : process(process) { }
    void init();
    bool start();
    bool stop();

    inline Process& getProcess(void) { return process; }
    inline bool getPostOutput(void)  { return postOutput; }
  private:
    Process& process;

    // Driver info
    AsioDrivers asioDrivers;
    ASIODriverInfo driverInfo;
    ASIOCallbacks asioCallbacks;
    bool postOutput;                    // PostOutput optimisation flag
    long inputChannels;
    long outputChannels;
	long minSize;
	long maxSize;
	long preferredSize;
	long granularity;
	ASIOSampleRate sampleRate;

	// Buffers
	ASIOBufferInfo bufferInfos[16];
};
