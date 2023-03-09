package com.example.appshoppingdatn.presentation.ui.base.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<T : ViewDataBinding> :AppCompatActivity() {
    lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,getLayoutResourceId())
        initControls(savedInstanceState)
    }
    abstract fun getLayoutResourceId(): Int

    abstract fun initControls(savedInstanceState: Bundle?)
}