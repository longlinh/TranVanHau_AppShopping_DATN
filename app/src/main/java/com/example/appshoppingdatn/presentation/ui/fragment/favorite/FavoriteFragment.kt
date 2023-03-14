package com.example.appshoppingdatn.presentation.ui.fragment.favorite

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentFavoriteBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_favorite
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }
}