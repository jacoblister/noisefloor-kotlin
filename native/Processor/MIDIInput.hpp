#include "../include/midiEvent.hpp"

#include <vector>
#include <array>

class MIDIInput {
public:
    bool polyPhonic;
    int channels;

    std::vector<std::array<float, 3>> channelData;
    int triggerClear;
public:
    inline void start(int sampleRate) {
    }

    inline void processMidi(std::vector<MIDIEvent> midiInput) {
        this->triggerClear = 2;
    }

    inline std::vector<std::array<float, 3>>& process() {
        if (this->triggerClear > 0) {
            this->triggerClear--;
            if (this->triggerClear == 0) {
                // Clear triggers
                for (int i = 0; i < this->channels; i++) {
                    this->channelData[i][2] = 0;
                }
            }
        }
        return this->channelData;
    }
};