package ru.pepelaz.foffree.activities.species

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_species.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.adapters.SpeciesAdapter


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
