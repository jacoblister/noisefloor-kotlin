#include "ClientMock.hpp"

//#include <unistd.h>
#include <thread>

void ClientMock::init() {
}

void ClientMock::run() {
    while(true) {
        std::this_thread::sleep_for(std::chrono::seconds(1));
//        usleep(1000000);
    }
}