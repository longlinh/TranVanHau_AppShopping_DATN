package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentHomeBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }
}