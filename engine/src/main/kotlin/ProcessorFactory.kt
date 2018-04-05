package org.noisefloor.engine

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