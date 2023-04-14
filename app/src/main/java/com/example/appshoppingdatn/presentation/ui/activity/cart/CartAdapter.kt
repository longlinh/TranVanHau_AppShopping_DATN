package com.example.appshoppingdatn.presentation.ui.activity.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemCartBinding
import com.example.appshoppingdatn.model.Cart
import java.text.DecimalFormat

class CartAdapter(val inters : ICart) : RecyclerView.Adapter<CartAdapter.Companion.CartViewModel>() {

    companion object{
        class CartViewModel(val binding : ItemCartBinding) : RecyclerView.ViewHolder(binding.root)
    }

    interface ICart {
        fun getCount()  :Int
        fun getData(position : Int) : Cart
        fun getContext() : Context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewModel {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewModel(binding)
    }

    override fun getItemCount(): Int {
        return inters.getCount()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewModel, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val carts = inters.getData(position)
        holder.binding.txtNameCart.text = carts.nameCart
        Glide.with(inters.getContext()).load(carts.imgCart).error(R.drawable.load_img).into(holder.binding.imgCart)
        holder.binding.txtPriceCart.text = decimalFormat.format(carts.priceCart)+"đ"
        holder.binding.txtNumberOderCart.text = carts.numberOder.toString()
        val number = holder.binding.txtNumberOderCart.text.toString().toInt()
        val sumPrice = number * carts.priceCart
        holder.binding.txtSumPriceCart.text = decimalFormat.format(sumPrice)+"đ"

        holder.binding.btnMinus.setOnClickListener {

        }
        holder.binding.btnPlus.setOnClickListener {

        }

    }
}