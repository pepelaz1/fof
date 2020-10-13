package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(name = "hourly", strict = false)
class Hourly {
    // @set:Element(name="precipMM")
    @field:Element(name = "precipMM")
    var precipMM: Double? = null

    // @set:Element(name="weatherDesc")
    @field:Element(name = "weatherDesc")
    var weatherDesc: String? = null

    // @set:Element(name="weatherIconUrl")
    @field:Element(name = "weatherIconUrl")
    var weatherIconUrl: String? = null

    // @set:Element(name="windspeedKmph")
    @field:Element(name = "windspeedKmph")
    var windspeedKmph: Int? = null

    // @set:Element(name="windspeedMiles")
    @field:Element(name = "windspeedMiles")
    var windspeedMiles: Int? = null

    // @set:Element(name="winddir16Point")
    @field:Element(name = "winddir16Point")
    var winddir16Point: String? = null
}