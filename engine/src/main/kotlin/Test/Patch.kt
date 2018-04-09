package org.noisefloor.engine

class Patch : Processor(
        name    = "patch",
        inputs  = arrayOf("freq"),
        outputs = arrayOf("output")) {
    val oscillator = Oscillator()
    private val gain = Gain()

    override fun start(sampleRate: Int) {
        oscillator.start(sampleRate)
        gain.start(sampleRate)
    }

    fun process(freq: Float): Float {
        if (freq == 0f) {
            return 0f
        }

        oscillator.freq.value = freq
        var sample = oscillator.process()
        sample = gain.process(sample, 0.25f)

        return sample
    }
}