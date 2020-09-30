package ru.pepelaz.foffree.activities.species

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_species_navigate.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.InfoFragment
import ru.pepelaz.foffree.storages.SpeciesStorage



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
        (fragmentInfo as InfoFragment).moreUrl  = "file:///android_asset/species/"+ specie.name.toLowerCase() + "/" +
                specie.name.toLowerCase() + "AppOnePage/" + specie.name.toLowerCase() + "AppOnePage.htm"
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
