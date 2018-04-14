package org.noisefloor.engine

class Patch : Processor(
        name    = "patch",
        inputs  = arrayOf("freq"),
        outputs = arrayOf("output")) {
    val oscillator = Oscillator()
    private val envelope = Envelope()
    private val gain = Gain()

    override fun start(sampleRate: Int) {
        oscillator.start(sampleRate)

        gain.master.value = 0.25f
        gain.exponential.value = true
        gain.start(sampleRate)

        envelope.start(sampleRate)
    }

    fun process(freq: Float, gate: Float, trigger: Float): Float {
        oscillator.freq.value = freq
        var sample = oscillator.process()
        val env = envelope.process(gate, trigger)

        sample = gain.process(sample, env)

        return sample
    }
}