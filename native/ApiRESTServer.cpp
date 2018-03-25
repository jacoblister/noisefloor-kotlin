#include <unistd.h>
#include "ApiRESTServer.hpp"

void ApiRESTServer::init() {
}

void ApiRESTServer::run() {
    while(1) {
        std::string response = this->process.query("some query", "some request");
        printf("Query response %s\n", response.c_str());

        usleep(1000000);
    }
}