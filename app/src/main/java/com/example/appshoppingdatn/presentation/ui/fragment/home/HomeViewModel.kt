package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.model.NewModel
import com.example.appshoppingdatn.model.Sale
import com.example.appshoppingdatn.model.SaleModel
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class HomeViewModel : BaseViewModel() {
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    val uiEventLiveData = SingleLiveData<Int>()
    var listNewModel = MutableLiveData<NewModel>()
    var listSaleModel = MutableLiveData<SaleModel>()
    private  var apiService : APIService ?= null
    private var compositeDisposable = CompositeDisposable()

    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
        if (Utils.listNewsModel != null){
            listNewModel.value = Utils.listNewsModel
        }else{
            getSPNew()
        }
        if (Utils.listSalesMode != null){
            listSaleModel.value = Utils.listSalesMode
        }else{
            getSPSale()

        }

    }

    private fun getSPSale() {
        compositeDisposable.addAll(apiService!!.getSPSale()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                saleModel ->
                run {
                    if (saleModel.success) {
                        listSaleModel.value = SaleModel(saleModel.success, saleModel.message, saleModel.result)
                        Utils.listSalesMode = saleModel
                    }
                }
            })
    }

    private fun getSPNew() {
        compositeDisposable.addAll(apiService!!.getSPNew()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    newModel ->
                run {
                    if (newModel.success) {
                        listNewModel.value = NewModel(newModel.success,newModel.message,newModel.result)
                        Utils.listNewsModel = newModel
                    }
                }
            })
    }

    fun onInsertFavoriteToSQLite(idFav : Int, imgFav : String , nameFav : String ,priceNew : Float , priceOld : Float, discription : String , type : String , selled : Int, status : Int , checkFav : String,identifier: Int, context : Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("INSERT INTO FAVORITE1 VALUES(null, '$idAccount' , '$idFav' , '$imgFav','$nameFav' ,'$priceNew','$priceOld','$discription','$type','$selled','$status','$checkFav','$identifier')")
    }
    fun onDeleteFavoriteToSQLite(id : Int, context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM FAVORITE1 WHERE IdSP = '$id' ")
    }
    fun onGetStatus(news: New,context: Context,img :ImageView) {
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE1 WHERE IdAccount = '$idAccount' AND IdSP = '${news.IdNew}'")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            val identifier = data.getInt(12)
            news.FavStatus = favStatus
            if (favStatus == 1 && identifier == 0){
                img.setImageResource(R.drawable.baseline_favorite_24)
            }else{
                img.setImageResource(R.drawable.no_favorite)
            }
        }
    }
    fun onGetStatusSale(sales: Sale,context: Context,img :ImageView) {
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE1 WHERE IdAccount = '$idAccount' AND IdSP = '${sales.IdSale}'")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            val identifier = data.getInt(12)
            sales.FavStatusSale = favStatus
            if (favStatus == 1 && identifier == 1){
                img.setImageResource(R.drawable.baseline_favorite_24)
            }else{
                img.setImageResource(R.drawable.no_favorite)
            }
        }
    }
}