package ru.pepelaz.fof.network

import android.content.Context
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.IOException


class Communicator(context: Context) {
    companion object {
        val baseUrl = "https://api.worldweatheronline.com/premium/v1/"
        var instance: Communicator? = null

        fun service(context: Context): WeatherService? {
            if (instance == null) {
                instance = Communicator(context)
            }
            return instance!!.weatherService
        }
    }

    var context: Context? = null
    var weatherService: WeatherService? = null

    init {
        this.context = context

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


//        val interceptor = Interceptor { chain ->
//            var request = chain.request()
//            val builder = request.newBuilder().addHeader("Cache-Control", "no-cache")
//            request = builder.build()
//            chain.proceed(request)
//        }

        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Cache-Control", "no-store")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val httpClient = builder
                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache( null)
                //.addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
                .baseUrl(baseUrl)
                .client(httpClient)
                .build()

        weatherService = retrofit.create(WeatherService::class.java!!)
    }
}