package ru.pepelaz.foffree.models

import org.simpleframework.xml.*
import java.util.*

@Root(strict = false)
class Tides() {
    @set:ElementList(inline=true, name="tide_data")
    @get:ElementList(inline=true, name="tide_data") var tide_data: ArrayList<Tide_data>? = null
}