package ru.pepelaz.fof.storages

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.support.v4.content.ContextCompat
import android.view.ContextThemeWrapper
import ru.pepelaz.fof.App
import ru.pepelaz.fof.data.Specie
import ru.pepelaz.fof.models.Weather
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.experimental.coroutineContext

object SpeciesStorage {
    private var species = ArrayList<Specie>()

    init {
        loadSpecies()
    }

    private fun loadSpecies() {

        val streamReader = InputStreamReader( App.instance.applicationContext.assets.open("species/species.csv"))
        val reader = BufferedReader(streamReader)
        var line: String?
        while (true) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            var parts = line!!.split("\t")
            species.add(Specie(parts[0], parts[1], parts[2]))
        }
    }

    fun getAt(n: Int): Specie {
        return species.get(n)
    }

    fun getSize(): Int {
        return species.size
    }

}