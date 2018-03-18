//val processorFactory = ProcessorFactory()
//val gain = processorFactory.build(Gain::class)
//val oscillator = processorFactory.build(Oscillator::class)

@JsName(name = "oscillator")
val oscillator = Oscillator()

@JsName(name = "gain")
val gain = Gain()

val lfo = Oscillator()

@JsName(name = "start")
fun start(sampleRate: Int) {
    println("starting with sampling rate $sampleRate")
    oscillator.start(sampleRate)
    lfo.start(sampleRate)
}

@JsName(name = "process")
fun process(samples: Array<Float>): Array<Float> {
    for (i in 0 until samples.size) {
        samples[i] = oscillator.process()
        samples[i] = gain.process(samples[i], 1f)
    }

//    lfo.freq = 5f
//    for (i in 0 until samples.size) {
//        samples[i] *= lfo.process() * 5
//    }

    return samples
}

//private fun jsonToMap(json: Json): Map<String, String> {

fun main(args : Array<String>) {
    val params = oscillator.getParameters()
    params[1].value = params[1].enum!![2]
    println(params[1].value)

    val event = JSON.parse<MIDIEvent>("""
        {"data":["abc",4,2]}
        """)
    println(event.data[0])
    println(JSON.stringify(gain))
}