package noisefloor.engine

data class Connector(val inProcessor: Processor, val inPort: Int,
                     val outProcessor: Processor, val outPort: Int)
