abstract class Module {
    var name: String = ""

    open fun start(sampleRate: Int) {}
    open fun stop() {}

    open fun process(frame: AudioFrame) {}
}