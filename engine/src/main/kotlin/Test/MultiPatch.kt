package org.noisefloor.engine

class MultiPatch : Processor(
        name    = "patch",
        inputs  = arrayOf("freqs"),
        outputs = arrayOf("output")) {
    private val channels = 4
    private val patches: Array<Patch> = Array(channels) { Patch() }

    @JsName("setWaveform")
    private fun setWaveform(wave: Oscillator.Waveform) {
        for (i in 0 until channels) {
            patches[i].oscillator.waveform.value = wave
        }
    }

    override fun start(sampleRate: Int) {
        for (i in 0 until channels) {
            patches[i].start(sampleRate)
        }
    }

    fun process(freqs: Array<Array<Float>>): Float {
        var result = 0f
        for (i in 0 until channels) {
            result += patches[i].process(freqs[i][0], freqs[i][1], freqs[i][2])
        }
        return result
    }
}