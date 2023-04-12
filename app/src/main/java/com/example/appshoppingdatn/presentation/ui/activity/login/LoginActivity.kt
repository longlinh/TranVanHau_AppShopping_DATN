package com.example.appshoppingdatn.presentation.ui.activity.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.databinding.ActivityLoginBinding
import com.example.appshoppingdatn.presentation.ui.fragment.signin.SigninFragment
import com.example.appshoppingdatn.presentation.ui.fragment.signup.SignupFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private var viewPagerAdapter: ViewPagerAdapter? = null

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_login
    }

    @SuppressLint("ResourceType")
    override fun initControls(savedInstanceState: Bundle?) {
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor(getString(R.color.backgound_main)))
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(SigninFragment(),getString(R.string.txtSignin))
        viewPagerAdapter!!.addFragment(SignupFragment(),getString(R.string.txtSignup))
        mBinding.viewPager.adapter = viewPagerAdapter
    }
    class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}