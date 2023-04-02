package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentHomeBinding
import com.example.appshoppingdatn.model.Banner
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.model.Sale
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.NewAdapter
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.SaleAdapter
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.ViewPagerAdapter
import com.example.appshoppingdatn.ultis.Utils
import java.util.ArrayList

class HomeFragment : BaseFragment<FragmentHomeBinding>() , SaleAdapter.ISale , NewAdapter.INew {
    private var current = 0
    private var runnable: Runnable? = null
    private var handler: Handler? = null
    private var saleAdapter : SaleAdapter ?= null
    private var newAdapter : NewAdapter ?= null
    private lateinit var viewModel: HomeViewModel

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        customViewpager()
        newAdapter = NewAdapter(this@HomeFragment)
        saleAdapter = SaleAdapter(this@HomeFragment)
        onDemoDataSale()
        onShowDataNew()

    }

    private fun onShowDataNew() {
        if (isConnectedInternet(requireContext())){
            viewModel.listNewModel.observe(this){
                val linearLayoutManager = GridLayoutManager(context,2)
                binding.recylerNew.layoutManager = linearLayoutManager
                binding.recylerNew.adapter = newAdapter
            }
            Log.d("new",viewModel.listNewModel.toString())
        }else{
            showMessage("No internet !")
        }
    }

    @SuppressLint("WrongConstant")
    private fun onDemoDataSale() {
        if (isConnectedInternet(requireContext())){
            viewModel.listSaleModel.observe(this){
                val linearLayoutManager = LinearLayoutManager.HORIZONTAL
                binding.recylerFlashSale.layoutManager = LinearLayoutManager(context,linearLayoutManager,false)
                binding.recylerFlashSale.adapter = saleAdapter
            }
            Log.d("new",viewModel.listSaleModel.toString())
        }else{
            showMessage("No internet !")
        }
    }

    private fun customViewpager() {
        val pagerArrayList: ArrayList<Banner> = ArrayList()
        pagerArrayList.add(Banner(R.drawable.banner4))
        pagerArrayList.add(Banner(R.drawable.banner5))
        pagerArrayList.add(Banner(R.drawable.banner3))
        pagerArrayList.add(Banner(R.drawable.banner6))
        pagerArrayList.add(Banner(R.drawable.banner2))
        pagerArrayList.add(Banner(R.drawable.banner1))
        val viewPageAdapter = ViewPagerAdapter(pagerArrayList)
        binding.viewPager.adapter = viewPageAdapter
        binding.circleIndicator.setViewPager(binding.viewPager)
        handler = Handler()
        runnable = Runnable {
            current = binding.viewPager.currentItem
            current++
            if (current >= binding.viewPager.adapter!!.count) {
                current = 0
            }
            binding.viewPager.setCurrentItem(current, true)
            handler!!.postDelayed(runnable!!, 3000)
        }
        handler!!.postDelayed(runnable!!, 3000)
    }

    override fun getCountSale(): Int {
        return viewModel.listSaleModel.value!!.result.size
    }

    override fun getDataSale(position: Int): Sale {
       return viewModel.listSaleModel.value!!.result[position]
    }

    override fun getContextSale(): Context {
        return requireContext()
    }

    override fun onClickInsertSaleToFavorite(sales: Sale) {
        val checkFav = "sale"
        viewModel.onInsertFavoriteToSQLite(sales.IdSale,sales.ImageSale,sales.NameSale,sales.PriceSaleNow,sales.PriceSaleOld,sales.DiscriptionSale,sales.TypeSale,sales.SelledSale,sales.FavStatusSale,checkFav,requireContext())
        showMessage("Đã thêm vào danh sách yêu thích !")
    }

    override fun onClickRemoveSaleFavorite(sales: Sale) {
        viewModel.onDeleteFavoriteToSQLite(sales.IdSale,requireContext())
        showMessage("Đã bỏ thích !")
    }

    override fun onStatusSaleFav(sales: Sale, img: ImageView) {
        viewModel.onGetStatusSale(sales,requireContext(),img)
    }


    override fun getCountNew(): Int {
        if (viewModel.listNewModel.value == null){
            return 0
        }
        return viewModel.listNewModel.value!!.result.size
    }

    override fun getDataNew(position: Int): New {
        return viewModel.listNewModel.value!!.result[position]
    }

    override fun onClickInsertToFavorite(news : New) {
        val priceOld = 0f
        val checkFav = "new"
        viewModel.onInsertFavoriteToSQLite(news.IdNew,news.ImageNew,news.NameNew,news.PriceNew, priceOld,news.DiscriptionNew,news.TypeNew,news.SelledNew,news.FavStatus,checkFav,requireContext())
        showMessage("Đã thêm vào danh sách yêu thích !")
    }

    override fun onClickRemoveFavorite(news : New) {
        viewModel.onDeleteFavoriteToSQLite(news.IdNew,requireContext())
        showMessage("Đã bỏ thích !")
    }

    override fun getContextNew(): Context {
        return requireContext()
    }

    override fun onStatusFav(news: New, img: ImageView) {
        viewModel.onGetStatus(news,requireContext(),img)
    }
}