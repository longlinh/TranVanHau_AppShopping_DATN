package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
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
        fun onLickInsertCategoryToFavorite(product: Product)
        fun onRemoveCategoryToFavorite(product: Product)
        fun onStatusCategoryFav(product: Product)
        fun onClickItemCategory(position: Int)
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
        Glide.with(inters.getContext()).load(category.ImageProduct).error(R.drawable.load_img).into(holder.binding.imgProduct)
        holder.binding.txtNameProduct.text = category.DescriptionProduct
        holder.binding.txtPriceProduct.text = decimalFormat.format(category.PriceProduct)+"đ"
        holder.binding.txtSelledProduct.text = inters.getContext().getString(R.string.txtSelled) +" "+ category.SelledProduct

        inters.onStatusCategoryFav(category)
        if (category.FavStatusProduct == 1){
            holder.binding.imgFavoriteProduct.setImageResource(R.drawable.baseline_favorite_24)
        }else{
            holder.binding.imgFavoriteProduct.setImageResource(R.drawable.no_favorite)
        }

        holder.binding.imgFavoriteProduct.setOnClickListener {
            if (category.FavStatusProduct == 0){
                category.FavStatusProduct = 1
                holder.binding.imgFavoriteProduct.setImageResource(R.drawable.baseline_favorite_24)
                inters.onLickInsertCategoryToFavorite(category)
            }else{
                category.FavStatusProduct = 0
                holder.binding.imgFavoriteProduct.setImageResource(R.drawable.no_favorite)
                inters.onRemoveCategoryToFavorite(category)
            }
        }
        holder.itemView.setOnClickListener {
            inters.onClickItemCategory(position)
        }
    }

}