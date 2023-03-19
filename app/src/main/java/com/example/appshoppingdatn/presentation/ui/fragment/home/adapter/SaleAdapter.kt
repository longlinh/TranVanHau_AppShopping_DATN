package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.imgSale.setImageResource(saler.imgSale)
        holder.binding.txtPercentSale.text = "-"+saler.percentSale+"%"
        holder.binding.txtNameSale.text = saler.nameSale
        holder.binding.txtPriceSaleOld.text = decimalFormat.format(saler.priceSaleOld)+"đ"
        holder.binding.txtPriceSaleNew.text = decimalFormat.format(saler.priceSaleNow)+"đ"
        holder.binding.txtSelled.text = "Đã bán " + saler.selledSale
    }
}