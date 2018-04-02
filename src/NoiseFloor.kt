//val processorFactory = ProcessorFactory()
//val gain = processorFactory.build(Gain::class)
//val oscillator = processorFactory.build(Oscillator::class)

@JsName(name = "oscillator")
val oscillator = Oscillator()

val oscillator2 = Oscillator()


@JsName(name = "gain")
val gain = Gain()

val lfo = Oscillator()

@JsName(name = "start")
fun start(sampleRate: Int) {
    println("starting with sampling rate $sampleRate")
    oscillator.start(sampleRate)
    lfo.start(sampleRate)

    oscillator2.waveform.value = Oscillator.Waveform.Saw
    oscillator2.start(sampleRate)
}

typealias AudioSamples = Array<Float>

@JsName(name = "process")
fun process(samplesIn: Array<AudioSamples>): Array<Float> {
    val samples = samplesIn[0]

    for (i in 0 until samples.size) {
        samplesIn[0][i] = oscillator.process()
        samplesIn[0][i] = gain.process(samples[i], 1f)
        samplesIn[1][i] = oscillator2.process()
    }

//    lfo.freq = 5f
//    for (i in 0 until samples.size) {
//        samples[i] *= lfo.process() * 5
//    }

    return samples
}

@JsName(name = "query")
fun query(endpoint: String, query: String): String {
    return "NoiseFloor says $endpoint : $query"
}

//private fun jsonToMap(json: Json): Map<String, String> {

fun main(args : Array<String>) {
    val event = MIDIEvent(data = arrayOf(1,2,3))

//    val params = oscillator.getParameters()
//    params[1].value = params[1].enum!![2]
//    println(params[1].value)

//    val event = JSON.parse<MIDIEvent>("""
//        {"data":["abc",4,2]}
//        """)
//    println(event.data[0])
//    println(JSON.stringify(gain))
}