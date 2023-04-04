package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentCategoryBinding
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.ultis.Utils

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() , CategoryAdapter.ICategory{
    private lateinit var viewModel: CategoryViewModel
    private var categoryAdapter : CategoryAdapter ?= null
    override fun getLayoutResId(): Int {
        return R.layout.fragment_category
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        categoryAdapter = CategoryAdapter(this@CategoryFragment)
        if (Utils.listProductModel != null){
            viewModel.listProductModel.value = Utils.listProductModel
        }else{

            viewModel.getDataCategory(CategoryViewModel.type)
        }
        onShowDataCategory()
    }

    private fun onShowDataCategory() {
        if (isConnectedInternet(requireContext())){
            viewModel.listProductModel.observe(this){
                val linearLayoutManager = GridLayoutManager(context,2)
                binding.recylerCategory.layoutManager = linearLayoutManager
                binding.recylerCategory.adapter = categoryAdapter
            }
        }else{
            showMessage("No internet !")
        }
    }

    override fun getCount(): Int {
        return viewModel.listProductModel.value!!.result.size
    }

    override fun getData(position: Int): Product {
        return viewModel.listProductModel.value!!.result[position]
    }

    override fun getContext(): Context {
        return requireActivity()
    }
}