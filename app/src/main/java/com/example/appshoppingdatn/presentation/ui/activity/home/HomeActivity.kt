package com.example.appshoppingdatn.presentation.ui.activity.home

import android.os.Bundle
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityHomeBinding
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>(){

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.txtData.text = "Hau"
    }



}