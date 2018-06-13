package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_species.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.data.Specie
import ru.pepelaz.fof.fragments.InfoFragment
import java.io.BufferedReader
import java.io.InputStreamReader


class SpeciesActivity : AppCompatActivity(), InfoFragment.IInfoFragment {

    var species = ArrayList<Specie>()
    var curIdx: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species)
        loadSpecies()
        (fragmentInfo as InfoFragment).listener = this
        openSpecie()
    }

    fun loadSpecies() {

        val streamReader = InputStreamReader(assets.open("species/species.csv"))

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



    fun openSpecie() {
        val specie = species[curIdx]
        val contentUrl = "file:///android_asset/species/"+ specie.name.toLowerCase() +"/" + specie.name.toLowerCase() + ".htm"
        (fragmentInfo as InfoFragment).contentUrl = contentUrl
        (fragmentInfo as InfoFragment).moreUrl = specie.moreUrl
        (fragmentInfo as InfoFragment).imageUrl = specie.imageUrl

        if (curIdx == 0) {
            (fragmentInfo as InfoFragment).onFirst()
        } else if (curIdx == species.size - 1) {
            (fragmentInfo as InfoFragment).onLast()
        } else {
            (fragmentInfo as InfoFragment).onMid()
        }

        (fragmentInfo as InfoFragment).updateContent()
    }

    override fun onNext() {
       if (curIdx + 1 <= species.size - 1) {
           curIdx++
           openSpecie()
       }
    }

    override fun onPrev() {
        if (curIdx - 1 >= 0) {
            curIdx--
            openSpecie()
        }
    }
}
