#include <stdio.h>

#include "ProcessV8Engine.hpp"
#include "DriverJack.hpp"
#include "ApiRESTServer.hpp"

class NoiseFloor {
  public:
    NoiseFloor(bool nothing) : process(), driver(process), api(process) {}

    void run(void);
  private:
    ProcessV8Engine process;
    DriverJack      driver;
    ApiRESTServer   api;
};

void NoiseFloor::run(void) {
    printf("run noisefloor\n");

    driver.init();
    driver.start();

    api.run();
}

int main(int argc, char* argv[]) {
    NoiseFloor noiseFloor(true);

    noiseFloor.run();

    printf("noisefloor\n");
    return 0;
}
