package noisefloor.engine.processor

import noisefloor.common.ProcessorParameter
import noisefloor.engine.Processor

class Envelope : Processor(
        name    = "envelope",
        inputs  = arrayOf("trigger"),
        outputs = arrayOf("output")) {
    val attack  = ProcessorParameter(value = 2f, min = 0f, max = 10000f)
    val decay   = ProcessorParameter(value = 100f, min = 0f, max = 10000f)
    val sustain = ProcessorParameter(value = 0.5f, min = 0f, max = 1f)
    val release = ProcessorParameter(value = 1000f, min = 0f, max = 10000f)

    private var sampleRate = 0
    private var output = 0f
    private var phase = 0
    private var delta = 0f

    override fun start(sampleRate: Int) {
        this.sampleRate = sampleRate
    }

    fun process(gate: Float, trigger: Float): Float {
        if (trigger > 0f) {
            output = 0f
            delta = (1000f / attack.value) / sampleRate
            phase = 1
        }

        when(phase) {
            0 -> {
                // Inactive
                if (gate > 0f) {
                    output = 0f
                    delta = (1000f / attack.value) / sampleRate
                    phase = 1
                }
            }
            1 -> {
                // Attack Phase
                output += delta
                if (output > 1f) {
                    delta = (1000f / decay.value) / sampleRate
                    phase = 2
                }
            }
            2 -> {
                // Decay Phase
                output -= delta
                if (output < sustain.value) {
                    phase = 3
                }
            }
            3 -> {
                // Sustain Phase
                if (gate == 0f) {
                    delta = (1000f / release.value) / sampleRate
                    phase = 4
                }
            }
            4 -> {
                // Release Phase
                output -= delta
                if (output < 0) {
                    output = 0f
                    phase = 0
                }
            }
        }

        return output
    }
}