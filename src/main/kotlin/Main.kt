package org.noisefloor.web
import org.noisefloor.engine.Gain

import org.w3c.dom.svg.*
import kotlin.browser.document

fun main(args : Array<String>) {
    val gain = Gain()
    println("Noisefloor web gain=${gain.master.value}")

    val svg = document.getElementById("keyboard") as SVGSVGElement
    val keyboard = Keyboard()
    svg.appendChild(keyboard.render())
}

fun webstart() {
    println("web start")
}