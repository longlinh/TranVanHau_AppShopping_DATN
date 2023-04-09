package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentCategoryBinding
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.presentation.ui.activity.detail.DetailsActivity
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
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
                CategoryViewModel.SHOW_MESSAGE_ERROR_CATEGORY -> onShowError()
            }
        }
        categoryAdapter = CategoryAdapter(this@CategoryFragment)
        viewModel.getDataCategory(CategoryViewModel.type)
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.layoutLoadingCategory.visibility = View.VISIBLE
                binding.layoutLoadingCategory.visibility = View.VISIBLE
            } else {
                binding.layoutLoadingCategory.visibility = View.GONE
                binding.layoutLoadingCategory.visibility = View.GONE
                binding.recylerCategory.visibility = View.VISIBLE
                binding.recylerCategory.visibility = View.VISIBLE
            }
        }
        onShowDataCategory()
        customBannerCategory()
        customBtnBack()
    }

    private fun customBtnBack() {
        binding.imgBack.setOnClickListener {
            startActivity(Intent(context,HomeActivity::class.java))
        }
    }

    private fun customBannerCategory() {
        if (CategoryViewModel.type == "laptop"){
            binding.imgBanner.setImageResource(R.drawable.banner1)
        }else if (CategoryViewModel.type == "watch"){
            binding.imgBanner.setImageResource(R.drawable.banner3)
        }else if (CategoryViewModel.type == "phone"){
            binding.imgBanner.setImageResource(R.drawable.banner6)
        }
    }

    private fun onShowError() {
        showMessage("Unable to load data from server.Please wait !!")
        viewModel.getDataCategory(CategoryViewModel.type)
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

    override fun onLickInsertCategoryToFavorite(product: Product) {
        val priceOld = 0f
        val checkFav = "category"
        viewModel.onInsertFavoriteToSQLite(
            product.IdProduct,
            product.ImageProduct,
            product.NameProduct,
            product.PriceProduct,
            priceOld,
            product.DescriptionProduct,
            product.TypeProduct,
            product.SelledProduct,
            product.FavStatusProduct,
            checkFav,
            requireContext())
        showMessage("Đã thêm vào danh sách yêu thích !")
    }

    override fun onRemoveCategoryToFavorite(product: Product) {
       viewModel.onDeleteFavoriteToSQLite(product.IdProduct,requireContext())
    }

    override fun onStatusCategoryFav(product: Product) {
       viewModel.onGetStatusSale(product,requireContext())
    }

    override fun onClickItemCategory(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        val dataProduct = viewModel.listProductModel.value!!.result[position]
        intent.putExtra("id",dataProduct.IdProduct)
        intent.putExtra("img",dataProduct.ImageProduct)
        intent.putExtra("name",dataProduct.NameProduct)
        intent.putExtra("price",dataProduct.PriceProduct)
        intent.putExtra("des",dataProduct.DescriptionProduct)
        intent.putExtra("sell",dataProduct.SelledProduct)
        startActivity(intent)
    }
}