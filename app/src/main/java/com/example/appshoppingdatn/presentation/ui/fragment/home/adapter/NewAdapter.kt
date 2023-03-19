package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.databinding.ItemNewBinding
import com.example.appshoppingdatn.model.New
import java.text.DecimalFormat

class NewAdapter(private val inter : INew) : RecyclerView.Adapter<NewAdapter.Companion.NewViewHolder>() {

    companion object{
        class NewViewHolder(val binding : ItemNewBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INew{
        fun getCountNew() : Int
        fun getDataNew(position : Int) : New
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val binding = ItemNewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return inter.getCountNew()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val news = inter.getDataNew(position)
        holder.binding.imgNew.setImageResource(news.imgNew)
        holder.binding.txtNameNew.text = news.nameNew
        holder.binding.txtPriceNew.text = decimalFormat.format(news.priceNew)+"đ"
        holder.binding.txtSelledNew.text = "Đã bán " + news.selledNew
    }
}