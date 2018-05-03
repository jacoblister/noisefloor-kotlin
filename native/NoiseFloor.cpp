#include "include/platform.hpp"
#include <iostream>
#include <thread>

#if PROCESS_MOCK
#include "ProcessMock.hpp"
#define PROCESS ProcessMock
#endif
#if PROCESS_V8
#define PROCESS ProcessV8Engine
#include "ProcessV8Engine.hpp"
#endif
#if PROCESS_CPP
#define PROCESS ProcessCPP
#include "ProcessCPP.hpp"
#endif

#if AUDIO_MOCK
#include "DriverMock.hpp"
#define DRIVER DriverMock
#endif
#if AUDIO_JACK
#include "DriverJack.hpp"
#define DRIVER DriverJack
#endif
#if AUDIO_ASIO
#include "DriverASIO.hpp"
#define DRIVER DriverASIO
#endif

#if CLIENT_MOCK
#include "ClientMock.hpp"
#define CLIENT ClientMock
#endif
#if CLIENT_RESTSERVER
#include "ClientRESTServer.hpp"
#define CLIENT ClientRESTServer
#endif

class NoiseFloor {
  public:
    NoiseFloor(bool nothing) : process(), driver(process), client(process) {}

    void run(void);
  private:
    PROCESS process;
    DRIVER driver;
    CLIENT client;
};

void NoiseFloor::run(void) {
    result<bool> result;

    driver.init();
    if (!(result = driver.start())) {
        std::cout << "Audio start failed: " << result.errorMessage() << std::endl;
        return;
    }

    if(!(result = client.start())) {
        std::cout << "Client Start Failed: " << result.errorMessage() << std::endl;
    }

    // Run until ESC pressed
    std::cout << "Press ESC to exit" << std::endl;
    while (getch() != 27) { }

    client.stop();
    driver.stop();
}

int main(int argc, char* argv[]) {
    NoiseFloor noiseFloor(true);

    noiseFloor.run();

    return 0;
}
