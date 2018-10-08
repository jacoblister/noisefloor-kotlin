package noisefloor.engine.test

import noisefloor.common.ProcessorParameter
import noisefloor.engine.processor.Oscillator
import noisefloor.engine.Processor

class MultiPatch : Processor(
        name    = "patch",
        inputs  = arrayOf("freqs"),
        outputs = arrayOf("output")) {
    val channels = ProcessorParameter(value = 4, min = 0f, max = 32f)
    private var patches: Array<Patch> = arrayOf()

    @JsName("setWaveform")
    private fun setWaveform(wave: Oscillator.Waveform) {
        for (i in 0 until channels.value) {
            patches[i].oscillator.waveform.value = wave
        }
    }

    override fun start(sampleRate: Int) {
        patches = Array(channels.value) { Patch() }
        for (i in 0 until channels.value) {
            patches[i].start(sampleRate)
        }
        setWaveform(Oscillator.Waveform.Square)
    }

    fun process(freqs: Array<Array<Float>>): Float {
        var result = 0f
        for (i in 0 until channels.value) {
            result += patches[i].process(freqs[i][0], freqs[i][1], freqs[i][2])
        }
        return result
    }
}