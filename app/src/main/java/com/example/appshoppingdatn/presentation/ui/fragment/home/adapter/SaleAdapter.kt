package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemSaleBinding
import com.example.appshoppingdatn.model.Sale
import java.text.DecimalFormat

class SaleAdapter(private val inters : ISale) : RecyclerView.Adapter<SaleAdapter.Companion.SaleViewHolder>() {
    companion object{
        class SaleViewHolder(val binding : ItemSaleBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface ISale{
        fun getCountSale() : Int
        fun getDataSale(position : Int) : Sale
        fun getContextSale() : Context
        fun onClickInsertSaleToFavorite(sales : Sale)
        fun onClickRemoveSaleFavorite(sales : Sale)
        fun onStatusSaleFav(sales : Sale , img : ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val binding = ItemSaleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SaleViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return inters.getCountSale()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val saler = inters.getDataSale(position)
        Glide.with(inters.getContextSale()).load(saler.ImageSale).into(holder.binding.imgSale)
        holder.binding.txtPercentSale.text = "-"+saler.PercentSale+"%"
        holder.binding.txtNameSale.text = saler.DiscriptionSale
        holder.binding.txtPriceSaleOld.text = decimalFormat.format(saler.PriceSaleOld)+"đ"
        holder.binding.txtPriceSaleNew.text = decimalFormat.format(saler.PriceSaleNow)+"đ"
        holder.binding.txtSelled.text = "Đã bán " + saler.SelledSale
        Log.d("listSale",saler.toString())

        inters.onStatusSaleFav(saler,holder.binding.imgFavorite)
        if (saler.FavStatusSale == 1){
            holder.binding.imgFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }else{
            holder.binding.imgFavorite.setImageResource(R.drawable.no_favorite)
        }
        holder.binding.imgFavorite.setOnClickListener {
            Log.d("sale",saler.toString())
            if (saler.FavStatusSale == 0){
                saler.FavStatusSale = 1
                holder.binding.imgFavorite.setImageResource(R.drawable.baseline_favorite_24)
                inters.onClickInsertSaleToFavorite(saler)
            }else{
                saler.FavStatusSale = 0
                holder.binding.imgFavorite.setImageResource(R.drawable.no_favorite)
                inters.onClickRemoveSaleFavorite(saler)
            }
        }
    }
}