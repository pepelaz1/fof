package ru.pepelaz.fof.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false)
class Weather() {
    @set:Element(name="date")
    @get:Element(name="date") var date: String? = null

    @set:Element(name="mintempC")
    @get:Element(name="mintempC") var mintempC: Int? = null

    @set:Element(name="maxtempC")
    @get:Element(name="maxtempC") var maxtempC: Int? = null

    @set:ElementList(inline=true)
    @get:ElementList(inline=true) var hourly: ArrayList<Hourly>? = null

    @set:Element(name="tides")
    @get:Element(name="tides") var tides: Tides? = null
}