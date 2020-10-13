package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(name = "tide_data", strict = false)
class Tide_data {
    // @set:Element(name="tideTime")
    @field:Element(name = "tideTime")
    var tideTime: String? = null

    // @set:Element(name="tideHeight_mt")
    @field:Element(name = "tideHeight_mt")
    var tideHeight_mt: Double? = null

    //@set:Element(name="tideDateTime")
    @field:Element(name = "tideDateTime")
    var tideDateTime: String? = null

    //@set:Element(name="tide_type")
    @field:Element(name = "tide_type")
    var tide_type: String? = null
}