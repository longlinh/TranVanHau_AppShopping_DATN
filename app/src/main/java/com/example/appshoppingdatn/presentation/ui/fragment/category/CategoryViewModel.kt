package com.example.appshoppingdatn.presentation.ui.fragment.category

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.model.Sale
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CategoryViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private  var apiService : APIService?= null
    private var compositeDisposable = CompositeDisposable()
    var listProductModel = MutableLiveData<ProductModel>()
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    companion object{
        var type = ""
        const val SHOW_MESSAGE_ERROR_CATEGORY = 1
    }
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
    }


    fun getDataCategory(type : String) {
        isLoading.value = true
        compositeDisposable.add(apiService!!.getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        productModel ->
                    run {
                        isLoading.value = false
                        if (productModel.success){
                            val listProduct = mutableListOf<Product>()
                            for (data in productModel.result) {
                                if (data.TypeProduct.equals(type) ){
                                    listProduct.add(data)
                                }
                            }
                            listProductModel.value = ProductModel(productModel.success,productModel.message,
                                listProduct as ArrayList<Product>
                            )
                            Utils.listProductModel = ProductModel(productModel.success,productModel.message,listProduct)
                        }
                    }
                },{
                    uiEventLiveData.value = SHOW_MESSAGE_ERROR_CATEGORY
                }
            )

        )
    }
    fun onInsertFavoriteToSQLite(idFav : String, imgFav : String , nameFav : String ,priceNew : Float , priceOld : Float, discription : String , type : String , selled : Int, status : Int , checkFav : String, context : Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("INSERT INTO FAVORITE2 VALUES(null, '$idAccount' , '$idFav' , '$imgFav','$nameFav' ,'$priceNew','$priceOld','$discription','$type','$selled','$status','$checkFav')")
    }
    fun onDeleteFavoriteToSQLite(id : String, context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM FAVORITE2 WHERE IdSP = '$id' ")
    }
    fun onGetStatusSale(product: Product, context: Context) {
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE2 WHERE IdAccount = '$idAccount' AND IdSP = '${product.IdProduct}' AND StatusFav = 1")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            product.FavStatusProduct = favStatus
        }
    }
}