package com.example.appshoppingdatn.presentation.ui.fragment.search

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentSearchBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    override fun getLayoutResId(): Int {
        return R.layout.fragment_search
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }
}