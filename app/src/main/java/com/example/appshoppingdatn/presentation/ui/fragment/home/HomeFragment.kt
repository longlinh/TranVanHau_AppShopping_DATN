package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
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
import java.util.ArrayList

class HomeFragment : BaseFragment<FragmentHomeBinding>() , SaleAdapter.ISale , NewAdapter.INew {
    private var current = 0
    private var runnable: Runnable? = null
    private var handler: Handler? = null
    private var listSale : ArrayList<Sale> ?= null
    private var saleAdapter : SaleAdapter ?= null
    private var listNew : ArrayList<New> ?= null
    private var newAdapter : NewAdapter ?= null
    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        customViewpager()
        listSale = ArrayList()
        listNew = ArrayList()
        newAdapter = NewAdapter(this)
        saleAdapter = SaleAdapter(this)
        onDemoDataSale()
        onDemoDataNew()
    }

    private fun onDemoDataNew() {
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        listNew!!.add(New(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,"","",150))
        val linearLayoutManager = GridLayoutManager(context,2)
        binding.recylerNew.layoutManager = linearLayoutManager
        binding.recylerNew.adapter = newAdapter
    }

    @SuppressLint("WrongConstant")
    private fun onDemoDataSale() {
        listSale!!.add(Sale(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",15500000f,17500000f,"","",120,13))
        listSale!!.add(Sale(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",17560000f,19500000f,"","",130,20))
        listSale!!.add(Sale(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",23599000f,33500000f,"","",1200,30))
        listSale!!.add(Sale(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",13500000f,15500000f,"","",190,23))
        listSale!!.add(Sale(0,R.drawable.iphone14,"[Mã AP1 giảm 5% tối đa 500k] Apple iphone 14 pro max 512GB",5500000f,8500000f,"","",520,15))
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

    override fun getCountNew(): Int {
      return listNew!!.size
    }

    override fun getDataNew(position: Int): New {
        return listNew!![position]
    }
}