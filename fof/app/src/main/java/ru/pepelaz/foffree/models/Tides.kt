package ru.pepelaz.foffree.models

import org.simpleframework.xml.*
import java.util.*

@Root(name = "tides", strict = false)
class Tides {
    //@set:ElementList(inline=true, name="tide_data")
    @field:ElementList(inline = true, name = "tide_data")
    var tide_data: List<Tide_data>? = null
}