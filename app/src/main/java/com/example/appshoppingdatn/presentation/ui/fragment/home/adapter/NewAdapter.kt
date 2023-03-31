package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.databinding.ItemNewBinding
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import java.text.DecimalFormat

class NewAdapter(private val inter : INew) : RecyclerView.Adapter<NewAdapter.Companion.NewViewHolder>() {
    companion object{
        class NewViewHolder(val binding : ItemNewBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INew{
        fun getCountNew() : Int
        fun getDataNew(position : Int) : New
        fun onClickInsertToFavorite(news : New)
        fun onClickRemoveFavorite(news: New)
        fun getContextNew() : Context
        fun onStatusFav(news : New , img : ImageView)
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
        Glide.with(inter.getContextNew()).load(news.ImageNew).into(holder.binding.imgNew)
        holder.binding.txtNameNew.text = news.DiscriptionNew
        holder.binding.txtPriceNew.text = decimalFormat.format(news.PriceNew)+"đ"
        holder.binding.txtSelledNew.text = "Đã bán " + news.SelledNew

        inter.onStatusFav(news,holder.binding.imgFavorite)

        holder.binding.imgFavorite.setOnClickListener {
            if (news.FavStatus == 0){
                news.FavStatus = 1
                holder.binding.imgFavorite.setImageResource(R.drawable.baseline_favorite_24)
                inter.onClickInsertToFavorite(news)
            }else{
                news.FavStatus = 0
                holder.binding.imgFavorite.setImageResource(R.drawable.no_favorite)
                inter.onClickRemoveFavorite(news)
            }
        }

    }

}