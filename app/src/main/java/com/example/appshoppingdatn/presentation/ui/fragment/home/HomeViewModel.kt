package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.*
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.xml.sax.ErrorHandler
import java.util.ArrayList

class HomeViewModel : BaseViewModel() {
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    val uiEventLiveData = SingleLiveData<Int>()
    var listNewModel = MutableLiveData<NewModel>()
    var listSaleModel = MutableLiveData<SaleModel>()
   // var listNoti : ArrayList<Notification> ?= null
    var numberNoti = MutableLiveData<Int>()
    private  var apiService : APIService ?= null
    private var compositeDisposable = CompositeDisposable()
    companion object{

        const val SHOW_MESSAGE_EROR_SALE = 1
        const val SHOW_MESSAGE_EROR_NEW = 2
    }
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        if (Utils.listNewsModel != null){
            listNewModel.value = Utils.listNewsModel
            isLoading.value = false
        }else{
            getSPNew()
        }
        if (Utils.listSalesMode != null){
            listSaleModel.value = Utils.listSalesMode
            isLoading.value = false
        }else{
            getSPSale()
        }
    }
     fun getSPSale() {
        isLoading.value = true
        compositeDisposable.addAll(apiService!!.getSPSale()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    saleModel ->
                run {
                    isLoading.value = false
                    if (saleModel.success) {
                        if (saleModel.result != null){
                            listSaleModel.value = SaleModel(saleModel.success, saleModel.message, saleModel.result)
                            Utils.listSalesMode = saleModel

                        }
                    }
                }
            },{
               uiEventLiveData.value = SHOW_MESSAGE_EROR_SALE
            })
        )
    }

     fun getSPNew() {
        isLoading.value = true
        compositeDisposable.addAll(apiService!!.getSPNew()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    newModel ->
                run {
                    isLoading.value = false
                    if (newModel.success) {
                        if (newModel.result != null){
                            listNewModel.value = NewModel(newModel.success,newModel.message,newModel.result)
                            Utils.listNewsModel = newModel

                        }
                    }
                }
            },{
                uiEventLiveData.value = SHOW_MESSAGE_EROR_NEW
            })
        )

    }

    fun onInsertFavoriteToSQLite(idFav : String, imgFav : String , nameFav : String ,priceNew : Float , priceOld : Float, discription : String , type : String , selled : Int, status : Int , checkFav : String, context : Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("INSERT INTO FAVORITE2 VALUES(null, '$idAccount' , '$idFav' , '$imgFav','$nameFav' ,'$priceNew','$priceOld','$discription','$type','$selled','$status','$checkFav')")
    }
    fun onDeleteFavoriteToSQLite(id : String, context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM FAVORITE2 WHERE IdSP = '$id' AND IdAccount = '$idAccount' ")
    }
    fun onGetStatus(news: New,context: Context,img :ImageView) {
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE2 WHERE IdAccount = '$idAccount' AND IdSP = '${news.IdNew}'")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            news.FavStatus = favStatus
            if (favStatus == 1){
                img.setImageResource(R.drawable.baseline_favorite_24)
            }else{
                img.setImageResource(R.drawable.no_favorite)
            }
        }
    }
    fun onGetStatusSale(sales: Sale,context: Context) {
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE2 WHERE IdAccount = '$idAccount' AND IdSP = '${sales.IdSale}' AND StatusFav = 1")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            sales.FavStatusSale = favStatus
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onShowDataNoti(context: Context){
        var count = 0
        Utils.notiArrayList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Shopping1.db", null, 2)
        val data = sqLiteHelper!!.getData("SELECT * FROM NOTIFICATION WHERE IdAccount = '$idAccount' ORDER BY Id DESC")
        while (data.moveToNext()) {
            val idTB = data.getInt(0)
            val txtTB = data.getString(2)
            val dateTB = data.getString(3)
            Utils.notiArrayList.add(Notification(idTB, txtTB, dateTB))
            count++
        }
        numberNoti.value = count

    }
}