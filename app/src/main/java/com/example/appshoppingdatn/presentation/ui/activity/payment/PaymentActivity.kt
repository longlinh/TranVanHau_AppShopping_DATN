package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityPaymentBinding
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.presentation.ui.activity.cart.CartActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.ultis.Utils
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {
    private lateinit var viewModel: PaymentViewModel
    private lateinit var productOrderAdapter : ProductOrderAdapter
    private lateinit var productArrayList : ArrayList<Cart>

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_payment
    }

    override fun initControls(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                PaymentViewModel.PAYMENT_SUCCESS -> paymentSuccess()
                PaymentViewModel.PAYMENT_FAILD -> paymentFaild()
            }
        }
        productArrayList = ArrayList()
        onSetDataPayment()
        onClickBack()
        onClickPayment()
        initDataProductOrder()
    }

    private fun paymentFaild() {
        showMessage("Thanh toan that bai")
    }

    private fun paymentSuccess() {
       showMessage("Thanh toan thanh cong")
    }

    private fun initDataProductOrder() {
        productArrayList = Utils.cartArrayList
        productOrderAdapter = ProductOrderAdapter(this,productArrayList)
        mBinding.recylerOrder.layoutManager = LinearLayoutManager(this)
        mBinding.recylerOrder.adapter = productOrderAdapter
    }

    private fun onClickPayment() {
        var time = System.currentTimeMillis()
        mBinding.btnPayment.setOnClickListener {
            time++
            val idBill = "HD$time"
            val nameUser = mBinding.txtNamePayment.text.toString().trim()
            val phoneUser = mBinding.txtPhonePayment.text.toString().trim()
            val sumPrice = Utils.payment
            val dateOrder = date()
            viewModel.onClickPayment(this,idBill,nameUser,phoneUser,sumPrice,mBinding.edtAddressPayment, dateOrder!!,productArrayList)
        }
    }

    private fun onClickBack() {
        mBinding.imgBackPayment.setOnClickListener {
            startActivity(Intent(this@PaymentActivity,CartActivity::class.java))
        }

    }
    @SuppressLint("SimpleDateFormat")
    fun date(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
        val cal = Calendar.getInstance()
        println(dateFormat.format(cal.time))
        return dateFormat.format(cal.time)
    }
    @SuppressLint("SetTextI18n")
    private fun onSetDataPayment() {
        val decimalFormat = DecimalFormat("###,###,###")
        viewModel.updateInfor(mBinding.txtNamePayment, mBinding.txtPhonePayment)
        mBinding.txtPricePayment.text = decimalFormat.format(Utils.payment)+"đ"
    }

}