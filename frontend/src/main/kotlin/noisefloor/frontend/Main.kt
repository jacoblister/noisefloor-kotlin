package noisefloor.frontend
//import org.noisefloor.engine.Gain
import noisefloor.common.data.MIDIEvent

import kotlinext.js.toPlainObjectStripNull

import org.w3c.dom.svg.*
import kotlin.browser.document

var mainKeyboard: Keyboard? = null

fun main(args : Array<String>) {
//    val gain = Gain()
//    println("Noisefloor web gain=${gain.master.value}")

    toPlainObjectStripNull(args)

    val svg = document.getElementById("keyboard") as SVGSVGElement
    val keyboard = Keyboard()
    svg.appendChild(keyboard.render())

    mainKeyboard = keyboard
}

fun getMIDIEvents(): Array<MIDIEvent> {
    return mainKeyboard!!.getNoteEvents()
}