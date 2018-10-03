package noisefloor.engine

import noisefloor.engine.common.AudioFrame

abstract class Module {
    var name: String = ""

    open fun start(sampleRate: Int) {}
    open fun stop() {}

    open fun process(frame: AudioFrame) {}
}