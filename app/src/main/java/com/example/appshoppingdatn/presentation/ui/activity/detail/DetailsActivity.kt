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
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_details
    }

    override fun initControls(savedInstanceState: Bundle?) {
        onGetDataDetail()
        onBack()
    }

    private fun onBack() {
        mBinding.imgBack.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
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
            mBinding.txtSell.text = "Đã bán $sellDetail"
            mBinding.txtDes.text = desDetail
        }

    }
}