package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_species.*
import kotlinx.android.synthetic.main.fragment_info.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.InfoFragment


class SpeciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species)

        (fragmentInfo as InfoFragment).textViewTitle.text = title
        (fragmentInfo as InfoFragment).moreUrl = "http://bit.ly/2kgUV78"
    }
}
