package com.example.appshoppingdatn.presentation.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.model.Banner

class ViewPagerAdapter(pagerList: ArrayList<Banner>) : PagerAdapter() {
    private var pagerList : ArrayList<Banner>?=null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.view_pager, container, false)
        val imgPager = view.findViewById<ImageView>(R.id.imgPager)
        val pager: Banner = pagerList!![position]
        imgPager.setImageResource(pager.resourcePager)
        //add view
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return pagerList?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    init {
        this.pagerList = pagerList
    }
}