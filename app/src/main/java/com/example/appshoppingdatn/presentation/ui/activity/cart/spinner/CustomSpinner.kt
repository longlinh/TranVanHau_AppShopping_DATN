package com.example.appshoppingdatn.presentation.ui.activity.cart.spinner

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.model.SpinnerData

class CustomSpinner(val activity : Activity , val listSpinner : List<SpinnerData>) : ArrayAdapter<SpinnerData>(activity, R.layout.item_spinner){

    override fun getCount(): Int {
        return listSpinner.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }
    fun initView(position: Int , convertView: View? , parent: ViewGroup) : View{
        val inflater = activity.layoutInflater
        val rowView = inflater.inflate(R.layout.item_spinner,parent,false)
        val imgVocher = rowView.findViewById<ImageView>(R.id.imgVoucher)
        val txtVoucher = rowView.findViewById<TextView>(R.id.txtVoucher)
        imgVocher.setImageResource(listSpinner[position].imgVoucher)
        txtVoucher.text = listSpinner[position].txtVoucher
        return rowView
    }
}