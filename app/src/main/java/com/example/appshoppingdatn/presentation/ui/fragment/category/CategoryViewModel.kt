package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private  var apiService : APIService?= null
    private var compositeDisposable = CompositeDisposable()
    var listProductModel = MutableLiveData<ProductModel>()

    companion object{
        var type = ""
    }
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
        Log.d("type", type)
    }

    fun getDataCategory(type : String) {
        compositeDisposable.add(apiService!!.getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe
                {
                    productModel ->
                    run {
                        if (productModel.success){
//                            listProductModel.value = ProductModel(productModel.success,productModel.message,productModel.result)
                            val listProduct = mutableListOf<Product>()
                            for (data in productModel.result) {
                                if (data.TypeProduct.equals(type) ){
                                    listProduct.add(data)
                                }
                            }
                            listProductModel.value = ProductModel(productModel.success,productModel.message,listProduct)
                            Utils.listProductModel = ProductModel(productModel.success,productModel.message,listProduct)
                        }
                    }
                })
    }
}