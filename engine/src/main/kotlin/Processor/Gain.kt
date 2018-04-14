package org.noisefloor.engine

import kotlin.math.pow

class Gain : Processor(
        name    = "getGain",
        inputs  = arrayOf("input", "getGain"),
        outputs = arrayOf("output")) {
    val master = ProcessorParameter(value = 1f, min = 0f, max = 1f)
    val exponential = ProcessorParameter(value = false)

    fun process(input: Float, gain: Float): Float {
        val sample = when (exponential.value) {
            false -> input * gain
            true  -> input * gain.pow(3)
//            true  -> input * 10f.pow(gain/20f*100f-5f)
        }
        return sample * master.value
    }
}