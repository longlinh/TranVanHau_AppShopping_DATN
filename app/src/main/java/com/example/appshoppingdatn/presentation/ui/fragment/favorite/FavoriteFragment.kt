package com.example.appshoppingdatn.presentation.ui.fragment.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentFavoriteBinding
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.presentation.ui.activity.detail.DetailsActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.favorite.adapter.FavoriteAdapter
import com.example.appshoppingdatn.ultis.Utils

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() , FavoriteAdapter.IFav{
    private var adapter : FavoriteAdapter ?= null
    private lateinit var viewModel: FavoriteViewModel

    override fun getLayoutResId(): Int {
        return R.layout.fragment_favorite
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        adapter = FavoriteAdapter(this)
        onShowDataFavorite()
        initRecylerview()
    }

    private fun onShowDataFavorite() {
        viewModel.onShowData(requireContext())
    }

    private fun initRecylerview() {
        val linearLayoutManager = GridLayoutManager(context,2)
        binding.recylerFavorite.layoutManager = linearLayoutManager
        binding.recylerFavorite.adapter = adapter
    }

    override fun getCount(): Int {
        if (viewModel.listFav == null){
            return 0
        }
        return viewModel.listFav!!.size
    }

    override fun getDataFav(position: Int): Favorite {
       return viewModel.listFav!![position]
    }

    override fun getContextFav(): Context {
        return requireContext()
    }

    override fun onCLickRemove(position: Int) {
        val fav = viewModel.listFav!![position]
        viewModel.onRemoveFav(fav.idFav,requireContext())
        viewModel.onRemoveItem(position)
        initRecylerview()
    }

    override fun onClickItemFavorite(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        val data = viewModel.listFav!![position]
        intent.putExtra("id",data.idFav)
        intent.putExtra("img",data.imgFav)
        intent.putExtra("name",data.nameFav)
        intent.putExtra("price",data.priceFavNow)
        intent.putExtra("des",data.discriptionFav)
        intent.putExtra("sell",data.selledFav)
        startActivity(intent)
    }
}