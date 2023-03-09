package com.example.appshoppingdatn.ui.activity.home

import android.os.Bundle
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.base.activity.BaseActivity
import com.example.appshoppingdatn.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.txtData.text = "Hau"
    }



}