package com.example.appTest.util

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.appTest.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_progress.view.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun progressDialog(
    context: Activity,
    message: String,
): AlertDialog {
    val contentView: View = context.layoutInflater.inflate(R.layout.dialog_progress, null)
    contentView.txtMessage.text = message

    MaterialAlertDialogBuilder(context)
        .setView(contentView)
        .create()
        .apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            window?.setBackgroundDrawable(context.getDrawable(R.drawable.background_dialog))
            return this
        }
}





