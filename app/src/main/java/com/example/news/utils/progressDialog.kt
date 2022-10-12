package com.example.news.utils

import android.app.ProgressDialog
import android.content.Context
import com.example.news.R

lateinit var progressDialog: ProgressDialog

fun createProgress(context: Context) {
    progressDialog = ProgressDialog(context)
    progressDialog.setCanceledOnTouchOutside(false)
    progressDialog.setCancelable(false)
    progressDialog.show()
    progressDialog.setContentView(R.layout.progress_dialog)
    progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
}

fun finishProgress() { progressDialog.dismiss() }