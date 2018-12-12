package ru.pepelaz.fof.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import ru.pepelaz.fof.R

class WarningDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_warning)
    }
}