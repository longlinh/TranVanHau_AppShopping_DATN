package com.example.appshoppingdatn.presentation.ui.base.activity

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.appshoppingdatn.R


abstract class BaseActivity<T : ViewDataBinding> :AppCompatActivity() {
    lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,getLayoutResourceId())
        initControls(savedInstanceState)
    }
    abstract fun getLayoutResourceId(): Int

    abstract fun initControls(savedInstanceState: Bundle?)

    fun showMessage(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    open fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment, Fragment::class.java.name)
            .commit()
    }
}