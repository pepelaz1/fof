package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(strict = false)
class Hourly {
    @set:Element(name="precipMM")
    @get:Element(name="precipMM") var precipMM: Double? = null

    @set:Element(name="weatherDesc")
    @get:Element(name="weatherDesc") var weatherDesc: String? = null

    @set:Element(name="weatherIconUrl")
    @get:Element(name="weatherIconUrl") var weatherIconUrl: String? = null

    @set:Element(name="windspeedKmph")
    @get:Element(name="windspeedKmph") var windspeedKmph: Int? = null

    @set:Element(name="windspeedMiles")
    @get:Element(name="windspeedMiles") var windspeedMiles: Int? = null

    @set:Element(name="winddir16Point")
    @get:Element(name="winddir16Point") var winddir16Point: String? = null
 }