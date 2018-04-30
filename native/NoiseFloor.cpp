#if PROCESS_MOCK
#include "ProcessMock.hpp"
#define PROCESS ProcessMock
#endif
#if PROCESS_V8
#define PROCESS ProcessV8
#include "ProcessV8Engine.hpp"
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

#include <iostream>
#include <thread>
#include <stdio.h>
#include <conio.h>

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
    driver.init();
    if (!driver.start()) {
        std::cout << "Audio start failed" << std::endl;
        return;
    }

    client.start();

    // Run until ESC pressed
    std::cout << "Press ESC to exit" << std::endl;
    while (true) {
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        if (kbhit() && getch() == 27) {
            break;
        }
    }

    client.stop();
    driver.stop();
}

int main(int argc, char* argv[]) {
    NoiseFloor noiseFloor(true);

    noiseFloor.run();

    return 0;
}
