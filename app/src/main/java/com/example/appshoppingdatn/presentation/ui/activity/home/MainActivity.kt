package com.example.appshoppingdatn.presentation.ui.activity.home

import android.os.Bundle
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.txtData.text = "Hau"
    }



}