package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(strict = false)
class Data {
    //var weather = ArrayList<Weather>()
    //@field:Element(name = "Request")
   //var request: Request? = null

  // @Path("request")
   @set:ElementList(inline=true)
   @get:ElementList(inline=true) var weather: ArrayList<Weather>? = null
}