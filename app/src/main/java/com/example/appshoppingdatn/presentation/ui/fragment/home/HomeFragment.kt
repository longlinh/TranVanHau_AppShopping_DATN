package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
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
    private var listSale : ArrayList<Sale> ?= null
    private var saleAdapter : SaleAdapter ?= null
    private var listNew : ArrayList<New> ?= null
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
        newAdapter = NewAdapter(this)
        saleAdapter = SaleAdapter(this)
        onDemoDataSale()
        onDemoDataNew()
    }

    private fun onDemoDataNew() {
        if(listNew == null || listNew!!.isEmpty()){
            listNew = ArrayList()
            listNew!!.add(New(1,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 11 pro max 512GB",11500000f,"","",110,0))
            listNew!!.add(New(2,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 12 pro max 512GB",12500000f,"","",120,0))
            listNew!!.add(New(3,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 13 pro max 512GB",13500000f,"","",130,0))
            listNew!!.add(New(4,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",14500000f,"","",140,0))
            listNew!!.add(New(5,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 15 pro max 512GB",15500000f,"","",150,0))
            listNew!!.add(New(6,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",16500000f,"","",150,0))
        }
        val linearLayoutManager = GridLayoutManager(context,2)
        binding.recylerNew.layoutManager = linearLayoutManager
        binding.recylerNew.adapter = newAdapter
    }

    @SuppressLint("WrongConstant")
    private fun onDemoDataSale() {
        if (listSale == null || listSale!!.isEmpty()){
            listSale = ArrayList()
            listSale!!.add(Sale(7,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,17500000f,"","",120,13,0))
            listSale!!.add(Sale(8,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",17560000f,19500000f,"","",130,20,0))
            listSale!!.add(Sale(9,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",23599000f,33500000f,"","",1200,30,0))
            listSale!!.add(Sale(10,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",13500000f,15500000f,"","",190,23,0))
            listSale!!.add(Sale(11,"https://tranvanhau2001.000webhostapp.com/Hinhanh/iphone14.png","[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",5500000f,8500000f,"","",520,15,0))
        }
        val linearLayoutManager = LinearLayoutManager.HORIZONTAL
        binding.recylerFlashSale.layoutManager = LinearLayoutManager(context,linearLayoutManager,false)
        binding.recylerFlashSale.adapter = saleAdapter
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
       return listSale!!.size
    }

    override fun getDataSale(position: Int): Sale {
       return listSale!![position]
    }

    override fun getContextSale(): Context {
        return requireContext()
    }

    override fun onClickInsertSaleToFavorite(sales: Sale) {
        val checkFav = "sale"
        viewModel.onInsertFavoriteToSQLite(sales.idSale,sales.imgSale,sales.nameSale,sales.priceSaleNow,sales.priceSaleOld,sales.discriptionSale,sales.typeSale,sales.selledSale,sales.fav_status,checkFav,requireContext())
        showMessage("Đã thêm vào danh sách yêu thích !")
    }

    override fun onClickRemoveSaleFavorite(sales: Sale) {
        viewModel.onDeleteFavoriteToSQLite(sales.idSale,requireContext())
        showMessage("Đã bỏ thích !")
    }

    override fun onStatusSaleFav(sales: Sale, img: ImageView) {
        viewModel.onGetStatusSale(sales,requireContext(),img)
    }


    override fun getCountNew(): Int {
      return listNew!!.size
    }

    override fun getDataNew(position: Int): New {
        return listNew!![position]
    }

    override fun onClickInsertToFavorite(news : New) {
        val priceOld = 0f
        val checkFav = "new"
        viewModel.onInsertFavoriteToSQLite(news.idNew,news.imgNew,news.nameNew,news.priceNew, priceOld,news.discriptionNew,news.typeNew,news.selledNew,news.fav_status,checkFav,requireContext())
        showMessage("Đã thêm vào danh sách yêu thích !")
    }

    override fun onClickRemoveFavorite(news : New) {
        viewModel.onDeleteFavoriteToSQLite(news.idNew,requireContext())
        showMessage("Đã bỏ thích !")
    }

    override fun getContextNew(): Context {
        return requireContext()
    }

    override fun onStatusFav(news: New, img: ImageView) {
        viewModel.onGetStatus(news,requireContext(),img)
    }
}