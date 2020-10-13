package ru.pepelaz.foffree.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "weather", strict = false)
class Weather {
    //  @set:Element(name="date")
    @field:Element(name = "date")
    var date: String? = null

    // @set:Element(name="mintempC")
    @field:Element(name = "mintempC")
    var mintempC: Int? = null

    // @set:Element(name="maxtempC")
    @field:Element(name = "maxtempC")
    var maxtempC: Int? = null

    // @set:ElementList(inline=true)
    @field:ElementList(inline = true)
    var hourly: List<Hourly>? = null

    //@set:Element(name="tides")
    @field:Element(name = "tides")
    var tides: Tides? = null
}