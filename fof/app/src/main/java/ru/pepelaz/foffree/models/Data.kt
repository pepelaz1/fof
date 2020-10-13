package ru.pepelaz.foffree.models

import org.simpleframework.xml.*

@Root(name = "data", strict = false)
 class Data {
   //var weather = ArrayList<Weather>()
   //@field:Element(name = "Request")
   //var request: Request? = null

   // @Path("request")
//   @set:ElementList(inline=true)
   @field:ElementList(entry = "weather", inline = true)
   var weather: List<Weather>? = null
}