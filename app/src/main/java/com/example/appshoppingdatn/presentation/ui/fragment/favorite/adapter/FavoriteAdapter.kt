package com.example.appshoppingdatn.presentation.ui.fragment.favorite.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.databinding.ItemFavoriteBinding
import com.example.appshoppingdatn.model.Favorite
import java.text.DecimalFormat

class FavoriteAdapter (private val inters : IFav) : RecyclerView.Adapter<FavoriteAdapter.Companion.FavoriteViewHolder>(){

    companion object{
        class FavoriteViewHolder(val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface IFav{
        fun getCount() : Int
        fun getDataFav(position : Int) : Favorite
        fun getContext() : Context
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
        Glide.with(inters.getContext()).load(fav.imgFav).into(holder.binding.imgFavorite)
        holder.binding.txtNameFav.text = fav.nameFav
        holder.binding.txtPriceFavNew.text = decimalFormat.format(fav.priceFavNow)+"đ"
        holder.binding.txtPriceFavOld.text = decimalFormat.format(fav.priceFavOld)+"đ"
        holder.binding.txtSelledFav.text = "Đã bán " + fav.selledFav
        holder.binding.imgLove.setOnClickListener {

        }
    }

}