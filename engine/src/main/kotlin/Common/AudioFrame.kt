package org.noisefloor.engine

data class AudioFrame(val audio: Array<AudioBuffer>, val midi: Array<MIDIEvent>)