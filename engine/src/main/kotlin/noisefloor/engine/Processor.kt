package noisefloor.engine

import noisefloor.common.ProcessorParameter

abstract class Processor(var name: String = "processor", val inputs: Array<String> = emptyArray(), val outputs: Array<String> = emptyArray()) {
    @JsName(name = "start")
    open fun start(sampleRate: Int) {}
    open fun stop() {}

//    open fun process(input: Array<Float>): Array<Float> { return input }

    open fun getInputs():  Array<String> { return emptyArray() }
    open fun getOutputs(): Array<String> { return emptyArray() }

    open fun getParameters(): Array<ProcessorParameter<Any>> {
        // Find all Parameters in Processor and return as Array

        return js("var result = []; " +
                "for (var name in this) { " +
                "   if (this[name].value != undefined) {" +
                "       this[name].name = name;" +
                "       result.push(this[name]) " +
                "   } " +
                "}" +
                "result") as Array<ProcessorParameter<Any>>
    }
}