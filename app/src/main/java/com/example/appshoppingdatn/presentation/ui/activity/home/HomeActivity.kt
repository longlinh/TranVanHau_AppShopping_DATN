package com.example.appshoppingdatn.presentation.ui.activity.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityHomeBinding
import com.example.appshoppingdatn.presentation.ui.activity.cart.CartActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.presentation.ui.fragment.chat.ChatFragment
import com.example.appshoppingdatn.presentation.ui.fragment.favorite.FavoriteFragment
import com.example.appshoppingdatn.presentation.ui.fragment.home.HomeFragment
import com.example.appshoppingdatn.presentation.ui.fragment.profile.ProfileFragment
import com.example.appshoppingdatn.ultis.ContextUtils
import com.example.appshoppingdatn.ultis.Utils
import java.util.*

class HomeActivity : BaseActivity<ActivityHomeBinding>(){
    private var backPressTime: Long = 0
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.bottomNavigation.background = null
        mBinding.bottomNavigation.menu.getItem(2).isEnabled = false
        replaceFragment(HomeFragment())
        customBottomNavigationView()
        val config = Configuration()
        var locale: Locale? = null
        locale = Locale(ContextUtils.language)
        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config,resources.displayMetrics)
        when(Utils.checkFragment){
            0 -> {
                replaceFragment(HomeFragment())
                mBinding.bottomNavigation.menu.findItem(R.id.bottom_home).isChecked = true
            }
            1 -> {
                replaceFragment(FavoriteFragment())
                mBinding.bottomNavigation.menu.findItem(R.id.bottom_favorite).isChecked = true
            }
            2 -> {
                replaceFragment(ChatFragment())
                mBinding.bottomNavigation.menu.findItem(R.id.bottom_chat).isChecked = true
            }
            3 -> {
                replaceFragment(ProfileFragment())
                mBinding.bottomNavigation.menu.findItem(R.id.bottom_profile).isChecked = true
            }
        }
        onClickToCart()
    }

    private fun onClickToCart() {
        mBinding.btnCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }


    private fun customBottomNavigationView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.bottom_home -> {
                    Utils.checkFragment = 0
                    replaceFragment(HomeFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_home).isChecked = true
                }
                R.id.bottom_favorite -> {
                    Utils.checkFragment = 1
                    replaceFragment(FavoriteFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_favorite).isChecked = true
                }
                R.id.bottom_chat -> {
                    Utils.checkFragment = 2
                    replaceFragment(ChatFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_chat).isChecked = true
                }
                R.id.bottom_profile -> {
                    Utils.checkFragment = 3
                    replaceFragment(ProfileFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_profile).isChecked = true
                }
            }
            false
        }
    }

    override fun onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
            System.exit(0)
            return
        } else {
            Toast.makeText(this, getString(R.string.txtShowDupliBack), Toast.LENGTH_SHORT).show()
        }
        backPressTime = System.currentTimeMillis()
    }

}