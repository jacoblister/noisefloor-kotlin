#if PROCESS_MOCK
#include "ProcessMock.hpp"
#define PROCESS ProcessMock
#endif
#if PROCESS_V8
#define PROCESS ProcessV8Engine
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

#if LINUX
#include <termios.h>
#include <unistd.h>
#include <assert.h>
#include <string.h>
int getch(void) {
      int c=0;

      struct termios org_opts, new_opts;
      int res=0;
          //-----  store old settings -----------
      res=tcgetattr(STDIN_FILENO, &org_opts);
      assert(res==0);
          //---- set new terminal parms --------
      memcpy(&new_opts, &org_opts, sizeof(new_opts));
      new_opts.c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHONL | ECHOPRT | ECHOKE | ICRNL);
      tcsetattr(STDIN_FILENO, TCSANOW, &new_opts);
      c=getchar();
          //------  restore old settings ---------
      res=tcsetattr(STDIN_FILENO, TCSANOW, &org_opts);
      assert(res==0);
      return(c);
}
#endif
#if WINDOWS
#include <conio.h>
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
    driver.init();
    if (!driver.start()) {
        std::cout << "Audio start failed" << std::endl;
        return;
    }

    client.start();

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
