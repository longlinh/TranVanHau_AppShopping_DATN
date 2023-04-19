package com.example.appshoppingdatn.presentation.ui.fragment.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemSearchBinding
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.presentation.ui.activity.detail.DetailsActivity
import java.text.DecimalFormat

class SearchAdapter(val context: Context, var listProduct : ArrayList<Product>) : RecyclerView.Adapter<SearchAdapter.Companion.SearchViewHolder>() , Filterable{

    private var mList = ArrayList<Product>()

    init {
        this.mList = listProduct
    }

    companion object{
        class SearchViewHolder(val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = listProduct[position]
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtNameSearch.text = product.NameProduct
        holder.binding.txtDescritionSearch.text = product.DescriptionProduct
        holder.binding.txtPriceSearch.text = decimalFormat.format(product.PriceProduct)+"đ"
        holder.binding.txtSellSearch.text = context.getString(R.string.txtSelled) +" "+ product.SelledProduct
        Glide.with(context).load(product.ImageProduct).error(R.drawable.load_img).into(holder.binding.imgSearch)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id",product.IdProduct)
            intent.putExtra("img",product.ImageProduct)
            intent.putExtra("name",product.NameProduct)
            intent.putExtra("price",product.PriceProduct)
            intent.putExtra("des",product.DescriptionProduct)
            intent.putExtra("sell",product.SelledProduct)
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val search = constraint.toString()
                if (search.isEmpty()) {
                    listProduct = mList
                } else {
                    val list = ArrayList<Product>()
                    for (products in mList) {
                        if (products.NameProduct.lowercase().contains(search.lowercase())) {
                            list.add(products)
                        }
                    }
                    listProduct = list
                }
                val filterResults = FilterResults()
                filterResults.values = listProduct
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listProduct = results.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }

}