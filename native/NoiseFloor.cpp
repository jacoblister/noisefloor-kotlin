#include <stdio.h>

#include "ProcessV8Engine.hpp"
#include "DriverJack.hpp"
#include "DriverMock.hpp"
#include "ClientRESTServer.hpp"

class NoiseFloor {
  public:
    NoiseFloor(bool nothing) : process(), driver(process), client(process) {}

    void run(void);
  private:
    ProcessV8Engine  process;
    DriverJack       driver;
//    DriverMock       driver;
    ClientRESTServer client;
};

void NoiseFloor::run(void) {
    printf("run noisefloor\n");

    driver.init();
    driver.start();

    client.run();
}

int main(int argc, char* argv[]) {
    NoiseFloor noiseFloor(true);

    noiseFloor.run();

    printf("noisefloor\n");
    return 0;
}
