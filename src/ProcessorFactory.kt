import kotlin.reflect.KClass

class ProcessorFactory {
    fun build(processorClass: KClass<*>): Processor {
        val processor = when(processorClass) {
            Gain::class -> Envelope()
            Gain::class -> Gain()
            Gain::class -> Oscillator()
            else -> { throw IllegalArgumentException("Invalid processor class") }
        }

        return processor
    }
}