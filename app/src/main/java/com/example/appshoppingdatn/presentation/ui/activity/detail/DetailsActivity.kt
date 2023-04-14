package com.example.appshoppingdatn.presentation.ui.activity.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityDetailsBinding
import com.example.appshoppingdatn.presentation.ui.activity.cart.CartActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.ultis.Utils
import java.text.DecimalFormat

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {
    private var numberOder = 1
    private var idDetail : String ?= null
    private var nameDetail : String ?= null
    private var imgDetail : String ?= null
    private var priceDetail : Float ?= null
    private var desDetail : String ?= null
    private var sellDetail : Int ?= null
    private lateinit var viewModel: DetailViewModel
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_details
    }

    override fun initControls(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        onGetDataDetail()
        onBack()
        onClickMinus()
        onClickPlus()
        onClickAddToCart()
        onClickBuyNow()
    }

    private fun onClickBuyNow() {
        mBinding.btnBuyNow.setOnClickListener {
            val numberOder = mBinding.txtNumberOder.text.toString().toInt()
            viewModel.buyNow(this, idDetail!!,imgDetail!!,nameDetail!!,priceDetail!!,desDetail!!,sellDetail!!,numberOder)
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

    private fun onClickAddToCart() {
        mBinding.btnAddtoCart.setOnClickListener {
            val numberOder = mBinding.txtNumberOder.text.toString().toInt()
            viewModel.addToCart(this, idDetail!!,imgDetail!!,nameDetail!!,priceDetail!!,desDetail!!,sellDetail!!,numberOder)
            showMessage(getString(R.string.messageAddToCartSuccess))
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
            idDetail = intent.getStringExtra("id")
            nameDetail =  intent.getStringExtra("name")
            imgDetail =  intent.getStringExtra("img")
            priceDetail =  intent.getFloatExtra("price",0f)
            desDetail =  intent.getStringExtra("des")
            sellDetail =  intent.getIntExtra("sell",0)
            val decimalFormat = DecimalFormat("###,###,###")
            mBinding.txtNameDetail.text = nameDetail
            Glide.with(this).load(imgDetail).error(R.drawable.load_img).into(mBinding.imgDetails)
            mBinding.txtPriceDetail.text = "đ"+decimalFormat.format(priceDetail)
            mBinding.txtSell.text =  getString(R.string.txtSelled)+" " +"$sellDetail"
            mBinding.txtDes.text = desDetail
        }

    }
}