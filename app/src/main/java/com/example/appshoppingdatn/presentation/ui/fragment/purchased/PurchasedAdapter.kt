package com.example.appshoppingdatn.presentation.ui.fragment.purchased

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.databinding.ItemPurchasedOrderBinding
import com.example.appshoppingdatn.model.Bill
import com.example.appshoppingdatn.presentation.ui.activity.payment.ProductOrderAdapter
import java.text.DecimalFormat
import java.util.ArrayList

class PurchasedAdapter(val context: Context, private val billArrayList : ArrayList<Bill>) : RecyclerView.Adapter<PurchasedAdapter.Companion.PurchasedViewHolder>(){

    companion object{
        class PurchasedViewHolder(val binding : ItemPurchasedOrderBinding) : RecyclerView.ViewHolder(binding.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedViewHolder {
        val binding = ItemPurchasedOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PurchasedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return billArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PurchasedViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val bill = billArrayList[position]
        holder.binding.txtBill.text = bill.idBill
        holder.binding.txtNamePurchased.text = bill.nameUser
        holder.binding.txtPhonePurchased.text = bill.phoneUser
        holder.binding.txtAddressPurchased.text = bill.address
        holder.binding.txtDatePurchased.text = bill.dateOrder
        holder.binding.txtPricePurchased.text = decimalFormat.format(bill.sumPrice)+"đ"
        val productOrderAdapter = ProductOrderAdapter(context, bill.productArrayList!!)
        holder.binding.recylerProductOrder.layoutManager = LinearLayoutManager(context)
        holder.binding.recylerProductOrder.adapter = productOrderAdapter
    }

}