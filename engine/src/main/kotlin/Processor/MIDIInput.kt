package org.noisefloor.engine

import kotlin.math.pow

class MIDIInput : Processor(
        name    = "midi-input",
        inputs  = arrayOf("midi"),
        outputs = arrayOf("freq", "trigger", "level")) {
    val polyphonic = ProcessorParameter(value = 0f, min = 0f, max = 1f)
    val channels   = ProcessorParameter(value = 4f, min = 0f, max = 32f)

    private var channelNotes: Array<Int>   = arrayOf()
    private var channelHertz: Array<Float> = arrayOf()
    private var channelLevel: Array<Float> = arrayOf()
    private var noteChannels: HashMap<Int, Int> = hashMapOf()
    private var nextChannel:  Int = 0

    override fun start(sampleRate: Int) {
        channelNotes = Array(channels.value.toInt()) {0}
        channelHertz = Array(channels.value.toInt()) {0f}
        channelLevel = Array(channels.value.toInt()) {0f}
        noteChannels = hashMapOf()
        nextChannel = 0
    }

    fun process(midiIn: Array<MIDIEvent>): Array<Float> {
        for (i in 0 until midiIn.size) {
            val note     = midiIn[i].data[1].toInt()
            val velocity = midiIn[i].data[2].toInt()

            if (velocity > 0) {
                // Calculate frequency and level for note
                val frequency = 440.0f * 2.0f.pow((note - 57).toFloat() / 12)
                val level = velocity.toFloat() / 127.0f

                // Allocate next free channel
                var targetChannel = nextChannel
                while (channelNotes[targetChannel] != 0) {
                    targetChannel++
                    if (targetChannel >= channels.value) { targetChannel = 0 }

                    // If all channels active use current target
                    if (targetChannel == nextChannel) {
                        channelNotes[targetChannel] = 0
                        noteChannels.remove(channelNotes[targetChannel])
                    }
                }

                // set next channel, round robin
                nextChannel = targetChannel + 1
                if (nextChannel >= channels.value) { nextChannel = 0 }

                // set channel active
                channelNotes[targetChannel] = note
                channelHertz[targetChannel] = frequency
                channelLevel[targetChannel] = level
                noteChannels[note] = targetChannel
            }
            else {
                // note release - free allocated channel
                val noteChannel = noteChannels[note]
                if (noteChannel != null) {
                    channelNotes[noteChannel] = 0
                    channelHertz[noteChannel] = 0f
                    channelLevel[noteChannel] = 0f
                    noteChannels.remove(note)
                }
            }

            println(channelHertz)
        }


        return channelHertz
    }
}