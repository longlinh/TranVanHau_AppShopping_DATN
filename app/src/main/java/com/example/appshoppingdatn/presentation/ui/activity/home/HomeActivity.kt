package com.example.appshoppingdatn.presentation.ui.activity.home

import android.os.Bundle
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityHomeBinding
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.presentation.ui.fragment.chat.ChatFragment
import com.example.appshoppingdatn.presentation.ui.fragment.favorite.FavoriteFragment
import com.example.appshoppingdatn.presentation.ui.fragment.home.HomeFragment
import com.example.appshoppingdatn.presentation.ui.fragment.profile.ProfileFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(){

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.bottomNavigation.background = null
        mBinding.bottomNavigation.menu.getItem(2).isEnabled = false
        replaceFragment(HomeFragment())
        customBottomNavigationView()
    }

    private fun customBottomNavigationView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_home).isChecked = true
                }
                R.id.bottom_favorite -> {
                    replaceFragment(FavoriteFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_favorite).isChecked = true
                }
                R.id.bottom_chat -> {
                    replaceFragment(ChatFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_chat).isChecked = true
                }
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment())
                    mBinding.bottomNavigation.menu.findItem(R.id.bottom_profile).isChecked = true
                }
            }
            false
        }
    }


}