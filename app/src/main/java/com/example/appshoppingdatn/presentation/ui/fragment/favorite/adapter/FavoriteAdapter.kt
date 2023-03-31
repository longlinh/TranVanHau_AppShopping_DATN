package com.example.appshoppingdatn.presentation.ui.fragment.favorite.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.databinding.ItemFavoriteBinding
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.ultis.Utils
import java.text.DecimalFormat

class FavoriteAdapter (private val inters : IFav) : RecyclerView.Adapter<FavoriteAdapter.Companion.FavoriteViewHolder>(){

    companion object{
        class FavoriteViewHolder(val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface IFav{
        fun getCount() : Int
        fun getDataFav(position : Int) : Favorite
        fun getContextFav() : Context
        fun onCLickRemove(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return inters.getCount()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val fav = inters.getDataFav(position)
        if (fav.priceFavOld == 0f){
            holder.binding.txtPriceFavOld.visibility = View.INVISIBLE
        }else{
            holder.binding.txtPriceFavOld.text = decimalFormat.format(fav.priceFavOld)+"đ"
        }
        Glide.with(inters.getContextFav()).load(fav.imgFav).into(holder.binding.imgFavorite)
        holder.binding.txtNameFav.text = fav.discriptionFav
        holder.binding.txtPriceFavNew.text = decimalFormat.format(fav.priceFavNow)+"đ"
        holder.binding.txtSelledFav.text = "Đã bán " + fav.selledFav

        holder.binding.imgLove.setOnClickListener {
            inters.onCLickRemove(position)
        }
    }

}