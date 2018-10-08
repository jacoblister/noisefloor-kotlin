package noisefloor.frontend
//import org.noisefloor.engine.Gain
import noisefloor.common.api.EngineAPI
import noisefloor.common.data.MIDIEvent

import org.w3c.dom.svg.*
import kotlin.browser.document

var mainKeyboard: Keyboard? = null

fun main(args : Array<String>) {
//    val gain = Gain()
//    println("Noisefloor web gain=${gain.master.value}")

    val svg = document.getElementById("keyboard") as SVGSVGElement
    val keyboard = Keyboard()
    svg.appendChild(keyboard.render())

    mainKeyboard = keyboard
}

@JsName(name = "setEngineAPI")
fun setEngineAPI(engineAPI: EngineAPI) {
    println("engineAPI graph: ${engineAPI.graph()}")
}

@JsName(name = "getMIDIEvents")
fun getMIDIEvents(): Array<MIDIEvent> {
    return mainKeyboard!!.getNoteEvents()
}