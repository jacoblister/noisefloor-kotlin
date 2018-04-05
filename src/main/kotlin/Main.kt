package org.noisefloor.web

import org.noisefloor.engine.Gain

fun main(args : Array<String>) {
    val gain = Gain()
    println("Noisefloor web gain=${gain.master.value}")
}

fun webstart() {
    println("web start")
}