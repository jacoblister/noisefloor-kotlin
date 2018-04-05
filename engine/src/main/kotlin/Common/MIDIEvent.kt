package org.noisefloor.engine

data class MIDIEvent(val time: Long = 0, val data: Array<Byte>)
