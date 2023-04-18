package com.example.appshoppingdatn.presentation.ui.fragment.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemSearchBinding
import com.example.appshoppingdatn.model.Product
import java.text.DecimalFormat

class SearchAdapter(private val inters : ISearch) : RecyclerView.Adapter<SearchAdapter.Companion.SearchViewHolder>() {
    companion object{
        class SearchViewHolder(val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface ISearch{
        fun getCount() :Int
        fun getContext() : Context
        fun getData(position : Int)  : Product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return inters.getCount()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = inters.getData(position)
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtNameSearch.text = product.NameProduct
        holder.binding.txtDescritionSearch.text = product.DescriptionProduct
        holder.binding.txtPriceSearch.text = decimalFormat.format(product.PriceProduct)+"đ"
        holder.binding.txtSellSearch.text = "Đã bán : ${product.SelledProduct}"
        Glide.with(inters.getContext()).load(product.ImageProduct).error(R.drawable.load_img).into(holder.binding.imgSearch)
    }

}