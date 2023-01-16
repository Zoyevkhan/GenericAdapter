
package com.example.genericadapter
import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.widget.ImageView


@SuppressLint("ResourceType")
public class Progress(context: Context?) :
    Dialog(context!!, R.style.Theme_Translucent_NoTitleBar) {
    var progressIV: ImageView
    init {
        setContentView(com.example.genericadapter.R.layout.progress_layout)
        progressIV = findViewById(com.example.genericadapter.R.id.progressIV)
    }

    override fun show() {
        super.show()
    }

    override fun hide() {
        super.hide()
    }

    override fun dismiss() {
        super.dismiss()
    }
}