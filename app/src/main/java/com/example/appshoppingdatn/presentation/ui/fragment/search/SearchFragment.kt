package com.example.appshoppingdatn.presentation.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentSearchBinding
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() , SearchAdapter.ISearch{
    private lateinit var searchAdapter : SearchAdapter
    override fun getLayoutResId(): Int {
        return R.layout.fragment_search
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context {
        TODO("Not yet implemented")
    }

    override fun getData(position: Int): Product {
        TODO("Not yet implemented")
    }
}