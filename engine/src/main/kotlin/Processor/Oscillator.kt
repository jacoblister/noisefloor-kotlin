package org.noisefloor.engine

import kotlin.math.PI
import kotlin.math.sin

class Oscillator : Processor(
        name    = "getOscillator",
        outputs = arrayOf("output")) {
    val freq     = ProcessorParameter(value = 440f, min = 10f, max = 20000f)
    val waveform = ProcessorParameter(value = Waveform.Sine, enum = Waveform.values())

    enum class Waveform { Sine, Saw, Square, Triangle }

    private val waveTable: Array<Array<Float>> = Array(Waveform.values().size, { arrayOf<Float>() })
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

    fun process(): Float {
        val result = waveTable[waveform.value.ordinal][currentSample.toInt()]

        currentSample += freq.value
        if (currentSample >= sampleRate) { currentSample -= sampleRate }

        return result
    }
}