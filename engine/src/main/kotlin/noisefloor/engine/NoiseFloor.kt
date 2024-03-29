package noisefloor.engine

import noisefloor.common.api.EngineAPI
import noisefloor.common.data.MIDIEvent
import noisefloor.engine.api.EngineAPIEmedded
import noisefloor.engine.processor.Gain
import noisefloor.engine.processor.MIDIInput
import noisefloor.engine.processor.Oscillator
import noisefloor.engine.test.MultiPatch

//val processorFactory = ProcessorFactory()
//val getGain = processorFactory.build(Processor.Gain::class)
//val getOscillator = processorFactory.build(Processor.Oscillator::class)

@JsName(name = "getOscillator")
val oscillator = Oscillator()
val oscillator2 = Oscillator()

@JsName(name = "getGain")
val gain = Gain()
val lfo = Oscillator()

val midiInput = MIDIInput()

@JsName(name = "getPatch")
val multiPatch = MultiPatch()

@JsName(name = "start")
fun start(sampleRate: Int) {
    println("starting with sampling rate $sampleRate")
    oscillator.start(sampleRate)
    lfo.start(sampleRate)

//    getOscillator.waveform.value = Processor.Oscillator.Waveform.Square
    oscillator2.waveform.value = Oscillator.Waveform.Saw
    oscillator2.start(sampleRate)

    val channels = 8
    midiInput.channels.value  = channels
    multiPatch.channels.value = channels
    multiPatch.start(sampleRate)
    midiInput.start(sampleRate)
}

typealias AudioSamples = Array<Float>

@JsName(name = "getEngineAPI")
fun getEngineAPI() : EngineAPI {
    return EngineAPIEmedded()
}

@JsName(name = "speedtest")
fun speedtest() {
    val timeStart = js("performance.now()")
    oscillator.start(48000)
    println("speedtest start: $timeStart")

    for (i in 0 until 48000) {
        for (j in 0 until 25600) {
            val sample = oscillator.process()
        }
    }

    val timeEnd = js("performance.now()")
    println("speedtest end:  $timeEnd")
}

@JsName(name = "process")
fun process(samplesIn: Array<AudioSamples>, samplesOut: Array<AudioSamples>, midiIn: Array<MIDIEvent>, midiOut: Array<MIDIEvent>) {
    midiInput.processMidi(midiIn)

    for (i in 0 until samplesOut[0].size) {
        val freqs = midiInput.process()
        val sample = multiPatch.process(freqs)
        samplesOut[0][i] = sample
        samplesOut[1][i] = sample

//        samplesOut[0][i] = oscillator.process()
//        samplesOut[1][i] = oscillator2.process()

//        val osc = getGain.process(getOscillator.process(), 1f)
//        val osc2 = getGain.process(getOscillator2.process(), 1f)
//
//        samplesOut[0][i] = osc
//        samplesOut[1][i] = osc2
    }

//    getLfo.freq = 5f
//    for (i in 0 until samples.size) {
//        samples[i] *= getLfo.process() * 5
//    }
}

var timeLast = 0
@JsName(name = "processLogPerformance")
fun processLogPerformance(samplesIn: Array<AudioSamples>, samplesOut: Array<AudioSamples>, midiIn: Array<MIDIEvent>, midiOut: Array<MIDIEvent>) {
    val timeStart = js("performance.now()")

    process(samplesIn, samplesOut, midiIn, midiOut)

    val timeEnd = js("performance.now()")
    val ratio = (timeEnd - timeStart) / (timeEnd - timeLast) * 100

//    println("$ratio")
    timeLast = timeStart
}

@JsName(name = "query")
fun query(endpoint: String, query: String): String {
    return "NoiseFloor says $endpoint : $query"
}

//private fun jsonToMap(json: Json): Map<String, String> {

fun main(args : Array<String>) {
//    val event = MIDIEvent(data = arrayOf(1, 2, 3))

//    val params = getOscillator.getParameters()
//    params[1].value = params[1].enum!![2]
//    println(params[1].value)

//    val event = JSON.parse<Common.MIDIEvent>("""
//        {"data":["abc",4,2]}
//        """)
//    println(event.data[0])
//    println(JSON.stringify(getGain))
}