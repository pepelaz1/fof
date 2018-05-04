package ru.pepelaz.fof.models

import org.simpleframework.xml.*

@Root(strict = false)
class Hourly {
    @set:Element(name="precipMM")
    @get:Element(name="precipMM") var precipMM: Double? = null

    @set:Element(name="weatherDesc")
    @get:Element(name="weatherDesc") var weatherDesc: String? = null

    @set:Element(name="weatherIconUrl")
    @get:Element(name="weatherIconUrl") var weatherIconUrl: String? = null
}