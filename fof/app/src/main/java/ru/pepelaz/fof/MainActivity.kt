package ru.pepelaz.fof

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewFish.setOnTouchListener(touchListener)
        imageViewRecords.setOnTouchListener(touchListener)
        imageViewTackle.setOnTouchListener(touchListener)
        imageViewBalt.setOnTouchListener(touchListener)
        imageViewFacts.setOnTouchListener(touchListener)
        imageViewSeasons.setOnTouchListener(touchListener)
        imageViewLocations.setOnTouchListener(touchListener)
        imageViewGlossary.setOnTouchListener(touchListener)
        imageViewCamera.setOnTouchListener(touchListener)
        imageViewCooking.setOnTouchListener(touchListener)
        imageViewTides.setOnTouchListener(touchListener)
        imageViewWeather.setOnTouchListener(touchListener)
        imageViewContact.setOnTouchListener(touchListener)
        imageViewAbout.setOnTouchListener(touchListener)
        imageViewGetLucky.setOnTouchListener(touchListener)
        imageViewQuiz.setOnTouchListener(touchListener)

        Log.d("test_test","Destiny: " + resources.getDisplayMetrics().density)
    }

    object touchListener: View.OnTouchListener {
        override fun onTouch(v: View, m: MotionEvent): Boolean {
            // Perform tasks here
            when(m.action) {
                MotionEvent.ACTION_DOWN -> (v as ImageView).alpha = 0.5.toFloat()
                MotionEvent.ACTION_UP -> (v as ImageView).alpha = 1.0.toFloat()
            }
            return true
        }
    }

    fun buttonQuitClicked(v: View) {
        finish()
    }
    fun buttonGoSiteClicked(v: View) {
        startActivity(Intent(this, SiteActivity::class.java))
        finish()
    }
}
