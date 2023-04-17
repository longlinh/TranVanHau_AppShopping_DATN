package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemOrderPaymentBinding
import com.example.appshoppingdatn.model.Cart
import java.text.DecimalFormat
import java.util.ArrayList

class ProductOrderAdapter(val context: Context ,val cartArrayList : ArrayList<Cart>) : RecyclerView.Adapter<ProductOrderAdapter.Companion.ProductOrderViewHolder>(){

    companion object{
        class ProductOrderViewHolder(val binding : ItemOrderPaymentBinding) : RecyclerView.ViewHolder(binding.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductOrderViewHolder {
       val binding = ItemOrderPaymentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductOrderViewHolder, position: Int) {
        val productOrder = cartArrayList[position]
        val decimalFormat = DecimalFormat("###,###,###")
        Glide.with(context).load(productOrder.imgCart).error(R.drawable.load_img).into(holder.binding.imgCart)
        holder.binding.txtNameCart.text = productOrder.nameCart
        holder.binding.txtPriceCart.text = decimalFormat.format(productOrder.priceCart)+"đ"
        holder.binding.txtNumberOrderPayment.text = productOrder.numberOder.toString()
    }

}