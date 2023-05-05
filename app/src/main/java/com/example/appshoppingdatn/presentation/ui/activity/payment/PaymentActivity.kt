package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityPaymentBinding
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.presentation.ui.activity.cart.CartActivity
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.ultis.Utils
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {
    private lateinit var viewModel: PaymentViewModel
    private lateinit var productOrderAdapter : ProductOrderAdapter
    private lateinit var productArrayList : ArrayList<Cart>
    private var PUBLISH_KEY = "pk_test_51LKdcILCmPlhYQx0U6PqKxvQfRWlLn0RVWnYPCRe90qa1eTpjpgN81bgvuA1nLVKyGrOuVGSWYiNtpp8HbBjwdgz00oIJqL5i1"
    private var paymentSheet : PaymentSheet ?=null
    var idBill = ""
    var nameUser = ""
    var sumPrice = 0f
    var dateOrder = ""
    var phoneUser = ""
    var time = System.currentTimeMillis()
    private var dialog : Dialog ?= null
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_payment
    }

    override fun initControls(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        productArrayList = ArrayList()
        onSetDataPayment()
        onClickBack()
        onClickPayment()
        initDataProductOrder()
        PaymentConfiguration.init(this,PUBLISH_KEY)
        paymentSheet = PaymentSheet(this) { paymentSheetResult: PaymentSheetResult? ->
            onPaymentResult(paymentSheetResult!!)
        }
        val pricePaymentDolla = (Utils.payment/23000).toInt()
        viewModel.onPayment(applicationContext,pricePaymentDolla)
    }

    private fun onPaymentResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult){
            is PaymentSheetResult.Canceled -> {
                showMessage(getString(R.string.paymentCancel))
            }
            is PaymentSheetResult.Failed -> {
                showMessage(getString(R.string.paymentFaild))
            }
            is PaymentSheetResult.Completed -> {
                OpenDialogDelete(Gravity.CENTER)
                val btnOk = dialog!!.findViewById<Button>(R.id.btnOkPayment)
                btnOk.setOnClickListener {
                    viewModel.onClickPayment(this,idBill,nameUser,phoneUser,sumPrice,mBinding.edtAddressPayment, dateOrder,productArrayList)
                    viewModel.deleteCart(this)
                    startActivity(Intent(this@PaymentActivity,HomeActivity::class.java))
                }
            }
        }
    }

    private fun initDataProductOrder() {
        productArrayList = Utils.cartArrayList
        productOrderAdapter = ProductOrderAdapter(this,productArrayList)
        mBinding.recylerOrder.layoutManager = LinearLayoutManager(this)
        mBinding.recylerOrder.adapter = productOrderAdapter
    }

    private fun onClickPayment() {

        mBinding.btnPayment.setOnClickListener {
            time++
            idBill = "HD$time"
            nameUser = mBinding.txtNamePayment.text.toString().trim()
            phoneUser = mBinding.txtPhonePayment.text.toString().trim()
            sumPrice = Utils.payment
            dateOrder = date()!!
            val address = mBinding.edtAddressPayment.text.toString().trim()
            if (address.isEmpty()){
                mBinding.edtAddressPayment.error = getString(R.string.txtMessageAddressEmpty)
            }else{
                PaymentFlow()
            }
        }
    }

    private fun PaymentFlow() {
        paymentSheet!!.presentWithPaymentIntent(
            viewModel.getClientSecret(), PaymentSheet.Configuration(
                "HAU Company", PaymentSheet.CustomerConfiguration(
                    viewModel.getCustomerID(),
                    viewModel.getEphericalKey()
                )
            )
        )
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

    private fun OpenDialogDelete(gravity: Int) {
        dialog = Dialog(this@PaymentActivity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_payment_success)
        val window = dialog!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialog!!.setCancelable(true)
        } else {
            dialog!!.setCancelable(false)
        }
        dialog!!.show()
    }
}