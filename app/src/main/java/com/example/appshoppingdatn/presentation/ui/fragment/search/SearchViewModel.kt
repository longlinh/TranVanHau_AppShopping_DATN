package com.example.appshoppingdatn.presentation.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class SearchViewModel : BaseViewModel() {
    private var compositeDisposable = CompositeDisposable()
    private  var apiService : APIService?= null
    val uiEventLiveData = SingleLiveData<Int>()
    var listProductModel = MutableLiveData<ProductModel>()
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
    }
    companion object{
        const val SHOW_MESSAGE_EROR_SEARCH = 1

    }
    fun getDataSearch(){
        compositeDisposable.add(apiService!!.getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        productModel ->
                    run {
                         isLoading.value = false
                        if (productModel.success){
                            if (productModel.result != null){
                                listProductModel.value = ProductModel(productModel.success,productModel.message,productModel.result)

                            }
                        }
                    }
                },{
                        uiEventLiveData.value = SHOW_MESSAGE_EROR_SEARCH
                }
            )
        )
    }
}