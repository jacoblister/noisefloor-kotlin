package noisefloor.engine

import noisefloor.engine.processor.Envelope
import noisefloor.engine.processor.Gain
import noisefloor.engine.processor.Oscillator
import kotlin.reflect.KClass

class ProcessorFactory {
    fun build(processorClass: KClass<*>): Processor {
        val processor = when(processorClass) {
            Envelope::class -> Envelope()
            Gain::class -> Gain()
            Oscillator::class -> Oscillator()
            else -> { throw IllegalArgumentException("Invalid processor class") }
        }

        return processor
    }
}