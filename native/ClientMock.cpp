#include "ClientMock.hpp"

#include <unistd.h>

void ClientMock::init() {
}

void ClientMock::run() {
    while(true) {
        usleep(1000000);
    }
}