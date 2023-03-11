package com.example.appshoppingdatn.ultis

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.example.appshoppingdatn.R

@SuppressLint("InflateParams")
class CustomProgressDialog(context: Context?) : Dialog(context!!) {
    init {
        val params = window!!.attributes
        params.gravity = Gravity.CENTER_HORIZONTAL
        window!!.attributes = params
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_loading, null)
        setContentView(view)
    }
}