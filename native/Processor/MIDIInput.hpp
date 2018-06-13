#include "../include/midiEvent.hpp"

#include <vector>
#include <array>
#include <unordered_map>

class MIDIInput {
public:
    bool polyPhonic;
    int channels = 4;

    std::vector<int> channelNotes;
    std::vector<std::array<float, 3>> channelData;
    std::unordered_map<int, int> noteChannels;
    int nextChannel;
    int triggerClear;
public:
    inline void start(int sampleRate) {
        this->channelNotes.resize(this->channels, 0);
        this->channelData.resize(this->channels, {0,0,0});
        this->noteChannels.clear();
        this->nextChannel = 0;
    }

    inline void processMidi(std::vector<MIDIEvent> midiInput) {
        for (int i = 0; i < midiInput.size(); i++) {
            struct MIDIEvent& midiEvent = midiInput.at(i);
            int note     = midiEvent.data[1];
            int velocity = midiEvent.data[2];
        }

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