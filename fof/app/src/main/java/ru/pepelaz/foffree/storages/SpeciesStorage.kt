package ru.pepelaz.foffree.storages

import ru.pepelaz.foffree.App
import ru.pepelaz.foffree.data.Specie
import java.io.BufferedReader
import java.io.InputStreamReader

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