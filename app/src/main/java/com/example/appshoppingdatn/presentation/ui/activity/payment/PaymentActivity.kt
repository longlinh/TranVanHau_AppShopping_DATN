package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityPaymentBinding
import com.example.appshoppingdatn.presentation.ui.activity.cart.CartActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.ultis.Utils
import java.text.DecimalFormat

class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {
    private lateinit var viewModel: PaymentViewModel
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_payment
    }

    override fun initControls(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                PaymentViewModel.ADDRESS_EMPTY -> showMesageAddressEmpty()
            }
        }
        onSetDataPayment()
        onClickBack()
        onClickPayment()
    }

    private fun onClickPayment() {
        viewModel.onClickPayment(this,mBinding.edtAddressPayment)
    }

    private fun showMesageAddressEmpty() {
        showMessage(getString(R.string.txtMessageAddressEmpty))
    }

    private fun onClickBack() {
        mBinding.imgBackPayment.setOnClickListener {
            startActivity(Intent(this@PaymentActivity,CartActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun onSetDataPayment() {
        val decimalFormat = DecimalFormat("###,###,###")
        viewModel.updateInfor( mBinding.txtNamePayment, mBinding.txtPhonePayment)
        mBinding.txtPricePayment.text = decimalFormat.format(Utils.payment)+"đ"
    }

}