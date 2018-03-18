class Gain : Processor(
        name ="gain",
        inputs = arrayOf("input", "gain"),
        outputs = arrayOf("output")) {
    val master = ProcessorParameter(value=1f, min=0f, max=1f)

    fun process(input: Float, gain: Float): Float {
        return input * gain * master.value
    }
}