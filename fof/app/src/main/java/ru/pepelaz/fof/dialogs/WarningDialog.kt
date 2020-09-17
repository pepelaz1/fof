package ru.pepelaz.fof.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import kotlinx.android.synthetic.main.dialog_warning.*
import ru.pepelaz.fof.R

class WarningDialog(context: Context) : Dialog(context) {

    var sharedPrefs = context.getSharedPreferences("fof",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_warning)
      //  getWindow().setLayout(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.heightPixels)
        getWindow()?.setLayout(context.resources.displayMetrics.widthPixels, WRAP_CONTENT)

        dontShowAgain.setOnClickListener({
            var editor = sharedPrefs.edit()
            editor.putBoolean("showWarningDialog", !dontShowAgain.isChecked)
            editor.apply()

        })
        initDontShowAgain()
    }

    fun initDontShowAgain() {
        dontShowAgain.isChecked = !sharedPrefs.getBoolean("showWarningDialog", false)
    }

}