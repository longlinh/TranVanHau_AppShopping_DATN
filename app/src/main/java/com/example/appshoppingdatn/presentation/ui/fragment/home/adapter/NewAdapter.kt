package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemNewBinding
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.ultis.Utils
import java.text.DecimalFormat

class NewAdapter(private val inter : INew) : RecyclerView.Adapter<NewAdapter.Companion.NewViewHolder>() {
    companion object{
        class NewViewHolder(val binding : ItemNewBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INew{
        fun getCountNew() : Int
        fun getDataNew(position : Int) : New
        fun onClickInsertToFavorite(news : New)
        fun onClickRemoveFavorite(news : New)
        fun getContextNew() : Context
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
        Glide.with(inter.getContextNew()).load(news.imgNew).into(holder.binding.imgNew)
        holder.binding.txtNameNew.text = news.nameNew
        holder.binding.txtPriceNew.text = decimalFormat.format(news.priceNew)+"đ"
        holder.binding.txtSelledNew.text = "Đã bán " + news.selledNew
        if (news.fav_status == 0){
            holder.binding.imgFavorite.setImageResource(R.drawable.no_favorite)
        }else{
            holder.binding.imgFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }
        holder.binding.imgFavorite.setOnClickListener {
            if (news.fav_status == 0){
                news.fav_status = 1
                holder.binding.imgFavorite.setImageResource(R.drawable.baseline_favorite_24)
                inter.onClickInsertToFavorite(news)
            }else{
                news.fav_status = 0
                holder.binding.imgFavorite.setImageResource(R.drawable.no_favorite)
                inter.onClickRemoveFavorite(news)
            }
        }
    }
}