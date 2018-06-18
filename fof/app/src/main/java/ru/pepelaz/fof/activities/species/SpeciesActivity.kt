package ru.pepelaz.fof.activities.species

import android.content.Intent
import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_species.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.adapters.SpeciesAdapter
import ru.pepelaz.fof.data.Specie
import ru.pepelaz.fof.fragments.InfoFragment
import ru.pepelaz.fof.storages.SpeciesStorage
import java.io.BufferedReader
import java.io.InputStreamReader


class SpeciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species)

        gridViewSpecies.adapter = SpeciesAdapter(this)
        gridViewSpecies.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, SpeciesNavigateActivity::class.java)
            val b = Bundle()
            b.putInt("specieNum", i)
            intent.putExtras(b)
            startActivity(intent)
        }
    }

    fun onHomeClick(v: View) {
        finish()
    }

    fun onNavigateClick(v: View) {
        startActivity(Intent(this, SpeciesNavigateActivity::class.java))
    }
}
