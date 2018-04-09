package org.noisefloor.web

import org.noisefloor.engine.MIDIEvent
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document

class Keyboard {
    private var noteEvents: Array<MIDIEvent> = emptyArray()
    private val noteMap: HashMap<Int, Element> = hashMapOf()

    fun getNoteEvents(): Array<MIDIEvent> {
        val result = noteEvents
        noteEvents = emptyArray()
        return result
    }

    private fun noteOn(keyNumber: Int) {
        noteEvents += MIDIEvent(0, arrayOf(0.toByte(), keyNumber.toByte(), 127.toByte()))
        noteMap[keyNumber]?.addClass("depressed")
    }

    private fun noteOff(keyNumber: Int) {
        noteEvents += MIDIEvent(0, arrayOf(0.toByte(), keyNumber.toByte(), 0.toByte()))
        noteMap[keyNumber]?.removeClass("depressed")
    }

    private fun keyboardInput() {
        val keyMap: Map<String, Int> = hashMapOf(
                "a" to 60, "w" to 61, "s" to 62, "e" to 63, "d" to 64,
                "f" to 65, "t" to 66, "g" to 67, "y" to 68, "h" to 69, "u" to 70, "j" to 71, "k" to 72)
        val keyDown: MutableSet<Int> = mutableSetOf()

        document.addEventListener("keydown",
                fun (event: Event) {
                    if (event is KeyboardEvent) {
                        val key = keyMap[event.key.toLowerCase()]
                        if (key != null && !keyDown.contains(key)) {
                            noteOn(key)
                            keyDown.add(key)
                        }
                    }
                }
        )

        document.addEventListener("keyup",
                fun (event: Event) {
                    if (event is KeyboardEvent) {
                        val key = keyMap[event.key.toLowerCase()]
                        if (key != null) {
                            noteOff(key)
                            keyDown.remove(key)
                        }
                    }
                }
        )
    }

    private enum class NoteType { White, Black }
    private fun renderNote(noteNumber: Int, noteType: NoteType, xPosition: Int): Element {
        val note: Element = when (noteType) {
            NoteType.White ->
                svgElement("rect", hashMapOf(
                    "id" to "note$noteNumber",
                    "x" to xPosition, "y" to 0, "width" to 40, "height" to 160,
                    "class" to "key-white"))
            NoteType.Black ->
                svgElement("rect", hashMapOf(
                    "id" to "note$noteNumber",
                    "x" to xPosition + 28, "y" to 0, "width" to 26, "height" to 120,
                    "class" to "key-black"))
        }
        noteMap[noteNumber] = note

        note.addEventListener("mouseenter",
                fun(event: Event) {
                    if (event is MouseEvent) {
                        if (event.buttons != 0.toShort()) {
                            noteOn(noteNumber)
                        }
                    }
                }
        )
        note.addEventListener("mousedown",  { noteOn(noteNumber)  } )
        note.addEventListener("mouseup",    { noteOff(noteNumber) } )
        note.addEventListener("mouseout",   { noteOff(noteNumber) } )

        return note
    }

    private fun renderOctave(group: Element, noteStart: Int, xStart: Int) {
        val blackNotes = setOf(1, 3, 6, 8, 10)

        for (noteType in NoteType.values()) {
            var xPos = xStart
            for (i in 0 until 12) {
                val noteNumber = noteStart + i

                if (i > 0 && !blackNotes.contains(i)) {
                    xPos += 40
                }
                if (noteType == NoteType.White && !blackNotes.contains(i)) {
                    group.appendChild(renderNote(noteNumber, noteType, xPos))
                }
                if (noteType == NoteType.Black && blackNotes.contains(i)) {
                    group.appendChild(renderNote(noteNumber, noteType, xPos))
                }
            }
        }
    }

    fun render(): Node {
        val group = svgElement("g")

        for (octave in 0 until 3) {
            renderOctave(group, 48 + octave * 12, (40 * 7 * octave) + 1)
        }

        keyboardInput()

        return group
    }
}
