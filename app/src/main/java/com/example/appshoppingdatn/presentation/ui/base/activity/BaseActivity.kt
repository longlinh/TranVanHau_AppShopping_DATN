package com.example.appshoppingdatn.presentation.ui.base.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.ultis.ContextUtils
import java.util.*


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
    //Kiem tra ket noi internet
    open fun isConnectedInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val networkMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkWifi != null && networkWifi.isConnected || networkMobile != null && networkMobile.isConnected
    }
}