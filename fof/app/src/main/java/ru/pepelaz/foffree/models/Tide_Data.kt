package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(strict = false)
class Tide_data {
    @set:Element(name="tideTime")
    @get:Element(name="tideTime") var tideTime: String? = null

    @set:Element(name="tideHeight_mt")
    @get:Element(name="tideHeight_mt") var tideHeight_mt: Double? = null

    @set:Element(name="tideDateTime")
    @get:Element(name="tideDateTime") var tideDateTime: String? = null

    @set:Element(name="tide_type")
    @get:Element(name="tide_type") var tide_type: String? = null
 }