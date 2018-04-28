package ru.pepelaz.fof.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false)
class Weather() {
    @set:Element(name="date")
    @get:Element(name="date") var date: String? = null
}