package com.example.appshoppingdatn

import android.os.Bundle
import com.example.appshoppingdatn.base.activity.BaseActivity
import com.example.appshoppingdatn.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.txtData.text = "Hau"
    }

}