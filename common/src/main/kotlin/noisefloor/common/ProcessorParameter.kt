package noisefloor.common

class ProcessorParameter<T>(
        var name:  String? = null,
        var value: T,
        val min:   Float = 0f,
        val max:   Float = 1f,
        val enum:  Array<T>? = null)
