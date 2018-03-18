class Envelope : Processor(
        name    = "envelope",
        inputs  = arrayOf("trigger"),
        outputs = arrayOf("output")) {
    val attack  = ProcessorParameter(value=100f,  min=0f, max=10000f)
    val decay   = ProcessorParameter(value=200f,  min=0f, max=10000f)
    val sustain = ProcessorParameter(value=0.5f,  min=0f, max=1f)
    val release = ProcessorParameter(value=1000f, min=0f, max=10000f)

    fun process(trigger: Float): Float {
        return if (trigger > 0) 1f else 0f
    }
}