package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentHomeBinding
import com.example.appshoppingdatn.model.Banner
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.model.Notification
import com.example.appshoppingdatn.model.Sale
import com.example.appshoppingdatn.presentation.ui.activity.detail.DetailsActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.category.CategoryFragment
import com.example.appshoppingdatn.presentation.ui.fragment.category.CategoryViewModel
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.NewAdapter
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.SaleAdapter
import com.example.appshoppingdatn.presentation.ui.fragment.home.adapter.ViewPagerAdapter
import com.example.appshoppingdatn.presentation.ui.fragment.location.LocationFragment
import com.example.appshoppingdatn.presentation.ui.fragment.notification.NotificationFragment
import com.example.appshoppingdatn.presentation.ui.fragment.notification.NotificationViewModel
import com.example.appshoppingdatn.presentation.ui.fragment.purchased.PurchasedFragment
import com.example.appshoppingdatn.presentation.ui.fragment.report.ReportFragment
import com.example.appshoppingdatn.presentation.ui.fragment.search.SearchFragment
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

    @SuppressLint("WrongConstant")
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                HomeViewModel.SHOW_MESSAGE_EROR_SALE -> onShowError()
                HomeViewModel.SHOW_MESSAGE_EROR_NEW -> onShowErrorNew()
            }
        }
        viewModel.isLoading.observe(this, Observer {
            if (it){
                binding.layoutLoadingNew.visibility = View.VISIBLE
                binding.layoutLoadingSale.visibility = View.VISIBLE
            }else{
                binding.layoutLoadingNew.visibility = View.GONE
                binding.layoutLoadingSale.visibility = View.GONE
                binding.recylerNew.visibility = View.VISIBLE
                binding.recylerFlashSale.visibility = View.VISIBLE
            }
        })
        customViewpager()
        newAdapter = NewAdapter(this@HomeFragment)
        saleAdapter = SaleAdapter(this@HomeFragment)
        onDemoDataSale()
        onShowDataNew()
        onClickCategory()
        onClickPurchased()
        onClickSearch()
        onClickNotification()
        onShowNumberNoti()
        onClickReport()
        onClickLocation()
    }

    private fun onClickLocation() {
        binding.layoutLocation.setOnClickListener {
            replaceFragment(LocationFragment())
        }
    }

    private fun onClickReport() {
        binding.layoutReport.setOnClickListener {
            replaceFragment(ReportFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        onShowNumberNoti()
    }
    @SuppressLint("SetTextI18n")
    private fun onShowNumberNoti() {
        viewModel.onShowDataNoti(requireActivity())
        viewModel.numberNoti.observe(this@HomeFragment) {
            if (it == 0) {
                binding.layoutNumber.visibility = View.GONE
            } else {
                binding.layoutNumber.visibility = View.VISIBLE
                binding.txtNumberNoti.text = it.toString()
            }
        }
    }

    private fun onClickNotification() {
        binding.imgNoti.setOnClickListener {
            replaceFragment(NotificationFragment())
        }
    }

    private fun onClickSearch() {
        binding.edtSearch.setOnFocusChangeListener { v, hasFocus ->
            replaceFragment(SearchFragment())
        }
    }

    private fun onClickPurchased() {
        binding.layoutPurchased.setOnClickListener {
            replaceFragment(PurchasedFragment())
        }
    }


    private fun onShowErrorNew() {
        showMessage(getString(R.string.onShowErrorLoadDataHome))
        viewModel.getSPNew()
    }

    private fun onClickCategory() {
        binding.layoutLaptop.setOnClickListener {
            CategoryViewModel.type = "laptop"
            replaceFragment(CategoryFragment())
        }
        binding.layoutWatch.setOnClickListener {
            CategoryViewModel.type = "watch"
            replaceFragment(CategoryFragment())
        }
        binding.layoutPhone.setOnClickListener {
            CategoryViewModel.type = "phone"
            replaceFragment(CategoryFragment())
        }
    }

    private fun onShowError() {
        showMessage(getString(R.string.onShowErrorLoadDataHome))
        viewModel.getSPSale()
    }

    private fun onShowDataNew() {
        if (isConnectedInternet(requireContext())){
            viewModel.listNewModel.observe(this){
                val linearLayoutManager = GridLayoutManager(context,2)
                binding.recylerNew.layoutManager = linearLayoutManager
                binding.recylerNew.adapter = newAdapter
            }
        }else{
            showMessage(getString(R.string.txtNoInternet))
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
        }else{
            showMessage(getString(R.string.txtNoInternet))
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
        showMessage(getString(R.string.txtAddToFavorite))
    }

    override fun onClickRemoveSaleFavorite(sales: Sale) {
        viewModel.onDeleteFavoriteToSQLite(sales.IdSale,requireContext())
        showMessage(getString(R.string.txtDissToFavorite))
    }

    override fun onStatusSaleFav(sales: Sale) {
        viewModel.onGetStatusSale(sales,requireContext())
    }

    override fun onClickItemSale(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        val dataSale = viewModel.listSaleModel.value!!.result[position]
        intent.putExtra("id",dataSale.IdSale)
        intent.putExtra("img",dataSale.ImageSale)
        intent.putExtra("name",dataSale.NameSale)
        intent.putExtra("price",dataSale.PriceSaleNow)
        intent.putExtra("des",dataSale.DiscriptionSale)
        intent.putExtra("sell",dataSale.SelledSale)
        startActivity(intent)
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
        showMessage(getString(R.string.txtAddToFavorite))
    }

    override fun onClickRemoveFavorite(news : New) {
        viewModel.onDeleteFavoriteToSQLite(news.IdNew,requireContext())
        showMessage(getString(R.string.txtDissToFavorite))
    }

    override fun getContextNew(): Context {
        return requireContext()
    }

    override fun onStatusFav(news: New, img: ImageView) {
        viewModel.onGetStatus(news,requireContext(),img)
    }

    override fun onClickItemNew(position: Int) {
        val intent = Intent(context,DetailsActivity::class.java)
        val dataNew = viewModel.listNewModel.value!!.result[position]
        intent.putExtra("id",dataNew.IdNew)
        intent.putExtra("img",dataNew.ImageNew)
        intent.putExtra("name",dataNew.NameNew)
        intent.putExtra("price",dataNew.PriceNew)
        intent.putExtra("des",dataNew.DiscriptionNew)
        intent.putExtra("sell",dataNew.SelledNew)
        startActivity(intent)
    }
}