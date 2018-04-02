//import kotlin.js.Math.pow
import kotlin.math.pow

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

//    oscillator.waveform.value = Oscillator.Waveform.Square
    oscillator2.waveform.value = Oscillator.Waveform.Saw
    oscillator2.start(sampleRate)
}

typealias AudioSamples = Array<Float>

@JsName(name = "process")
fun process(samplesIn: Array<AudioSamples>, samplesOut: Array<AudioSamples>, MIDIIn: Array<MIDIEvent>, MIDIOut: Array<MIDIEvent>) {
    for (i in 0 until MIDIIn.size) {

        val freq = 440.0f * 2.0f.pow((MIDIIn[i].data[1] - 57).toFloat() / 12)
        val level = MIDIIn[i].data[2].toFloat() / 127.0f

        oscillator.freq.value = freq
        gain.master.value     = level

        println("got typed frame: ${MIDIIn[i].time}:${MIDIIn[i].data}  freq=$freq level=$level")
    }

    for (i in 0 until samplesOut[0].size) {
        val osc = gain.process(oscillator.process(), 1f)

        samplesOut[0][i] = osc
        samplesOut[1][i] = osc
    }

//    lfo.freq = 5f
//    for (i in 0 until samples.size) {
//        samples[i] *= lfo.process() * 5
//    }
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