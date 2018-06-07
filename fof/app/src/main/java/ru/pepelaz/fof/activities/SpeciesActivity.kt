package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_species.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.data.Specie
import ru.pepelaz.fof.fragments.InfoFragment


class SpeciesActivity : AppCompatActivity(), InfoFragment.IInfoFragment {

    var species = ArrayList<Specie>()
    var curIdx: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species)
        loadSpecies()
       // (fragmentInfo as InfoFragment).textViewTitle.text = title
        (fragmentInfo as InfoFragment).listener = this
        openSpecie()
    }

    fun loadSpecies() {
        species.add(Specie("angler"))
        species.add(Specie("bass"))
        species.add(Specie("bream"))
    }

    fun openSpecie() {
        val specie = species[curIdx]
        val contentUrl = "file:///android_asset/species/"+ specie.name +"/index.htm"
        (fragmentInfo as InfoFragment).contentUrl = contentUrl
        (fragmentInfo as InfoFragment).moreUrl = "http://bit.ly/2kgUV78"

        if (curIdx == 0) {
            (fragmentInfo as InfoFragment).onFirst()
        } else if (curIdx == species.size - 1) {
            (fragmentInfo as InfoFragment).onLast()
        } else {
            (fragmentInfo as InfoFragment).onMid()
        }

        (fragmentInfo as InfoFragment).updateContent()

//        val ft = supportFragmentManager.beginTransaction()
//        ft.remove(fragmentInfo)
//        ft.add(R.id.fragmentInfo, fragmentInfo)
//        ft.commit()
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
