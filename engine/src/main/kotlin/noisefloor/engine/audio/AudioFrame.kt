package noisefloor.engine.audio

import noisefloor.common.data.MIDIEvent

data class AudioFrame(val audio: Array<AudioBuffer>, val midi: Array<MIDIEvent>)