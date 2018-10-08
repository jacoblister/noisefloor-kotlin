package noisefloor.engine.api

import noisefloor.common.api.EngineAPI

class EngineAPIEmedded : EngineAPI {
    override fun graph() : String {
        return "Embedded API"
    }
}
