package org.noisefloor.engine

import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

private var waveTable: Array<Array<Float>> = Array(Oscillator.Waveform.values().size, { arrayOf<Float>() })

class Oscillator : Processor(
        name    = "getOscillator",
        outputs = arrayOf("output")) {
    val freq     = ProcessorParameter(value = 440f, min = 10f, max = 20000f)
    val waveform = ProcessorParameter(value = Waveform.Sine, enum = Waveform.values())

    enum class Waveform { Sine, Saw, Square, Triangle }

    private var sampleRate = 0
    private var currentSample: Float = 0f

    override fun start(sampleRate: Int) {
        this.sampleRate = sampleRate

        for (i in 0 until sampleRate) {
            waveTable[Waveform.Sine.ordinal][i]     = sin((2f * PI.toFloat() * i) / sampleRate)
            waveTable[Waveform.Saw.ordinal][i]      = (i.toFloat() / (sampleRate / 2)) - 1f
            waveTable[Waveform.Square.ordinal][i]   = if (i < sampleRate / 2) -1f else 1f
            waveTable[Waveform.Triangle.ordinal][i] = if (i < sampleRate / 2)
                    (i.toFloat() / (sampleRate / 4)) - 1f else
                    1f - ((i.toFloat() - (sampleRate / 2)) / (sampleRate / 4))
        }
    }

    @Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
    private fun jsFloatToInt(value: Any): Int {
        return js("value | 0")
    }

    fun process(): Float {
        val result = waveTable[waveform.value.ordinal][jsFloatToInt(currentSample)]

        currentSample += freq.value
        if (currentSample >= sampleRate) { currentSample -= sampleRate }

        return result
    }
}