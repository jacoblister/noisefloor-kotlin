package org.noisefloor.web

import org.w3c.dom.Element
import kotlin.browser.document

fun svgElement(name: String, attributes: HashMap<String, Any> = hashMapOf()): Element {
    val node = document.createElementNS( "http://www.w3.org/2000/svg", name)

    for (attribute in attributes) {
        node.setAttribute(attribute.key, attribute.value.toString())
    }
    return node
}

fun Element.addClass(className: String) {
    val attrName = "class"
    val currentClasses = this.getAttribute(attrName)
    if (currentClasses != null) {
        val newClasses = "$currentClasses $className"
        this.setAttribute(attrName, newClasses)
    }
}

fun Element.removeClass(className: String) {
    val attrName = "class"
    val currentClasses = this.getAttribute(attrName)
    if (currentClasses != null) {
        val newClasses = currentClasses.replace(className, "").trim(' ')
        this.setAttribute(attrName, newClasses)
    }
}

