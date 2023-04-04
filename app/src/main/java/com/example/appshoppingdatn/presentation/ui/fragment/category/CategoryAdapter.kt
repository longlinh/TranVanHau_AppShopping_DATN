package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.databinding.ItemCategoryBinding
import com.example.appshoppingdatn.model.Product
import java.text.DecimalFormat

class CategoryAdapter(private val inters : ICategory) : RecyclerView.Adapter<CategoryAdapter.Companion.CategoryViewHolder>() {

    companion object{
        class CategoryViewHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface ICategory{
        fun getCount() : Int
        fun getData(position : Int) : Product
        fun getContext() : Context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return inters.getCount()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val category = inters.getData(position)
        Glide.with(inters.getContext()).load(category.ImageProduct).into(holder.binding.imgProduct)
        holder.binding.txtNameProduct.text = category.DescriptionProduct
        holder.binding.txtPriceProduct.text = decimalFormat.format(category.PriceProduct)+"đ"
        holder.binding.txtSelledProduct.text = "Đã bán " + category.SelledProduct

    }

}