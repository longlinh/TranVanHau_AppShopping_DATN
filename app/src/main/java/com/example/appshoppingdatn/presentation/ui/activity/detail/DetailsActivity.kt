package com.example.appshoppingdatn.presentation.ui.activity.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityDetailsBinding
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import java.text.DecimalFormat

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {
    private var numberOder = 1
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_details
    }

    override fun initControls(savedInstanceState: Bundle?) {
        onGetDataDetail()
        onBack()
        onClickMinus()
        onClickPlus()
        onClickAddToCart()
        onClickBuyNow()
    }

    private fun onClickBuyNow() {
        mBinding.btnBuyNow.setOnClickListener {

        }
    }

    private fun onClickAddToCart() {
        mBinding.btnAddtoCart.setOnClickListener {

        }
    }

    private fun onClickPlus() {
        mBinding.btnPlus.setOnClickListener {
            numberOder += 1
            mBinding.txtNumberOder.text = numberOder.toString()
        }
    }

    private fun onClickMinus() {
        mBinding.btnMinus.setOnClickListener {
            val number = mBinding.txtNumberOder.text.toString().toInt()
            if (number <= 1){
                mBinding.btnMinus.isEnabled = false
            }else{
                numberOder = number - 1
            }
            mBinding.txtNumberOder.text = numberOder.toString()
        }
    }

    private fun onBack() {
        mBinding.imgBack.setOnClickListener {
//            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onGetDataDetail() {
        val intent = intent
        if (intent != null){
            val idDetail = intent.getStringExtra("id")
            val nameDetail =  intent.getStringExtra("name")
            val imgDetail =  intent.getStringExtra("img")
            val priceDetail =  intent.getFloatExtra("price",0f)
            val desDetail =  intent.getStringExtra("des")
            val sellDetail =  intent.getIntExtra("sell",0)
            val decimalFormat = DecimalFormat("###,###,###")
            mBinding.txtNameDetail.text = nameDetail
            Glide.with(this).load(imgDetail).into(mBinding.imgDetails)
            mBinding.txtPriceDetail.text = "đ"+decimalFormat.format(priceDetail)
            mBinding.txtSell.text =  getString(R.string.txtSelled)+" " +"$sellDetail"
            mBinding.txtDes.text = desDetail
        }

    }
}