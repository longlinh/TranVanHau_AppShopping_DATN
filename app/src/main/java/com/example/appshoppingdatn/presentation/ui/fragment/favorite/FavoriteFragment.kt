package com.example.appshoppingdatn.presentation.ui.fragment.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentFavoriteBinding
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.favorite.adapter.FavoriteAdapter

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() , FavoriteAdapter.IFav{
    private var adapter : FavoriteAdapter ?= null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_favorite
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        adapter = FavoriteAdapter(this)
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getDataFav(position: Int): Favorite {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context {
        TODO("Not yet implemented")
    }
}