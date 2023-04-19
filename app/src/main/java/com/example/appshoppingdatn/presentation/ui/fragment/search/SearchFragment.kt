package com.example.appshoppingdatn.presentation.ui.fragment.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentSearchBinding
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.home.HomeFragment
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchAdapter : SearchAdapter
    private lateinit var viewModel: SearchViewModel
   // private var listSearch : ArrayList<Product> = ArrayList()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_search
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        viewModel.getDataSearch()
        iniRecylerView()
        viewModel.isLoading.observe(this){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
                binding.recylerSearch.visibility = View.VISIBLE
            }
        }
        onClickSearch()
        onClickBack()

    }

    private fun onClickBack() {
        binding.imgBackSearch.setOnClickListener {
            replaceFragment(HomeFragment())
        }

    }

    private fun iniRecylerView() {
        viewModel.listProductModel.observe(this){
            searchAdapter = SearchAdapter(requireActivity(),viewModel.listProductModel.value!!.result)
            binding.recylerSearch.layoutManager = LinearLayoutManager(requireActivity())
            binding.recylerSearch.adapter = searchAdapter
        }
    }

    private fun onClickSearch() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


}