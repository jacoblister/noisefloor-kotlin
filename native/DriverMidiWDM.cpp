#include "DriverMidiWDM.hpp"

result<bool> DriverMidiWDM::init(void) {
    return true;
}

result<bool> DriverMidiWDM::start(void) {
    return true;
}

std::vector<MIDIEvent> DriverMidiWDM::readMidiEvents(void) {
    std::vector<MIDIEvent> event(1);
    return event;
}

void DriverMidiWDM::writeMidiEvents(std::vector<MIDIEvent> midiIn) {
}

result<bool> DriverMidiWDM::stop(void) {
    return true;
}
