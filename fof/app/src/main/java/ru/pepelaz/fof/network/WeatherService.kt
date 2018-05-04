package ru.pepelaz.fof.network


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.pepelaz.fof.models.Data

interface WeatherService {

    @GET("marine.ashx")
    fun getWeather(@Query("key") key: String, @Query("q") q: String, @Query("tide") tide: String,
                   @Query("tp") tp: Int):  Observable<Data>
}