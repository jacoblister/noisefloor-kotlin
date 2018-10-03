package org.noisefloor.engine

import noisefloor.engine.processor.Gain
import kotlin.test.*

class GainTest {
    @Test fun defaultMasterValue() {
        val gain = Gain()
        assertEquals(1f, gain.master.value)
    }

    @Ignore fun baz() {
        fail("Doesn't work")
    }
}