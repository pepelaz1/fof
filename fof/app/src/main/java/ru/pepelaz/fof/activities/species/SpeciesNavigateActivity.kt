package ru.pepelaz.fof.activities.species

import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_species.*
import kotlinx.android.synthetic.main.activity_species_navigate.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.InfoFragment
import ru.pepelaz.fof.storages.SpeciesStorage



class SpeciesNavigateActivity : AppCompatActivity(), InfoFragment.IInfoFragment {

    var curIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species_navigate)
         (fragmentInfo as InfoFragment).listener = this

        val b = intent.extras
        if (b != null)
            curIdx = b.getInt("specieNum")

        openSpecie()
    }

    fun openSpecie() {

       val specie = SpeciesStorage.getAt(curIdx)
        val contentUrl = "file:///android_asset/species/"+ specie.name.toLowerCase() +"/" + specie.name.toLowerCase() + ".htm"
        (fragmentInfo as InfoFragment).contentUrl = contentUrl
        (fragmentInfo as InfoFragment).moreUrl = specie.moreUrl
        (fragmentInfo as InfoFragment).imageUrl = specie.imageUrl

        if (curIdx == 0) {
            (fragmentInfo as InfoFragment).onFirst()
        } else if (curIdx == SpeciesStorage.getSize() - 1) {
            (fragmentInfo as InfoFragment).onLast()
        } else {
            (fragmentInfo as InfoFragment).onMid()
        }

        (fragmentInfo as InfoFragment).updateContent()
    }

    override fun onNext() {
       if (curIdx + 1 <=  SpeciesStorage.getSize() - 1) {
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
