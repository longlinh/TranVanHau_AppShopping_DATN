package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentCategoryBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_category
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }
}