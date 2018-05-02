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

    inline Process& getProcess(void)                { return process;            }
    inline long getInputChannels(void)              { return inputChannels;      }
    inline long getOutputChannels(void)             { return outputChannels;     }
    inline long getPreferredSize(void)              { return preferredSize;      }
    inline ASIOBufferInfo*  getBufferInfo(void)     { return bufferInfo.data();  }
    inline ASIOChannelInfo* getChannelInfo(void)    { return channelInfo.data(); }
    inline bool getPostOutput(void)                 { return postOutput;         }
    inline std::vector<float *> getSamplesIn(void)  { return samplesIn;          }
    inline std::vector<float *> getSamplesOut(void) { return samplesOut;         }
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
	std::vector<ASIOBufferInfo>  bufferInfo;
	std::vector<ASIOChannelInfo> channelInfo;

	std::vector<float *> samplesIn;
	std::vector<float *> samplesOut;
};
