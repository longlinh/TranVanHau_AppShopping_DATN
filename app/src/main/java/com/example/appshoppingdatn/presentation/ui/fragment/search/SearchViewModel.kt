package com.example.appshoppingdatn.presentation.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.presentation.ui.fragment.category.CategoryViewModel
import com.example.appshoppingdatn.ultis.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : BaseViewModel() {
    private var compositeDisposable = CompositeDisposable()
    private  var apiService : APIService?= null
    val uiEventLiveData = SingleLiveData<Int>()
    var listProductModel = MutableLiveData<ProductModel>()
    var listProduct : java.util.ArrayList<Product> ?= null
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
    }
    fun getDataSearch(keySearch : String){
        listProduct!!.clear()
        compositeDisposable.add(apiService!!.getSearch(keySearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        productModel ->
                    run {
//                        isLoading.value = false
                        if (productModel.success){
                            listProductModel.value = ProductModel(productModel.success,productModel.message,productModel.result)
                            listProduct = productModel.result
                        }
                    }
                },{

                }
            )
        )
    }
}