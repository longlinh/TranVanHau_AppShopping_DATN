package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.content.Context
import android.widget.ImageView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.model.Sale
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import okio.Utf8

class HomeViewModel : BaseViewModel() {
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    val uiEventLiveData = SingleLiveData<Int>()

    companion object{

    }

    fun onInsertFavoriteToSQLite(idFav : Int, imgFav : String , nameFav : String ,priceNew : Float , priceOld : Float, discription : String , type : String , selled : Int, status : Int , checkFav : String, context : Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Shopping.db",null,1)
        sqLiteHelper!!.QueryData("INSERT INTO FAVORITE VALUES(null, '$idAccount' , '$idFav' , '$imgFav','$nameFav' ,'$priceNew','$priceOld','$discription','$type','$selled','$status','$checkFav')")
    }
    fun onDeleteFavoriteToSQLite(id : Int, context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping.db",null,1)
        sqLiteHelper!!.QueryData("DELETE FROM FAVORITE WHERE IdSP = '$id' ")
    }
    fun onGetStatus(news: New,context: Context,img :ImageView) {
        sqLiteHelper = SQLiteHelper(context, "Shopping.db", null, 1)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE WHERE IdAccount = '$idAccount' AND IdSP = '${news.idNew}'")
        while (data.moveToNext()) {
           val favStatus = data.getInt(10)
            news.fav_status = favStatus
            if (favStatus == 1){
                img.setImageResource(R.drawable.baseline_favorite_24)
            }else{
                img.setImageResource(R.drawable.no_favorite)
            }
        }
    }
    fun onGetStatusSale(sales: Sale,context: Context,img :ImageView) {
        sqLiteHelper = SQLiteHelper(context, "Shopping.db", null, 1)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE WHERE IdAccount = '$idAccount' AND IdSP = '${sales.idSale}'")
        while (data.moveToNext()) {
            val favStatus = data.getInt(10)
            sales.fav_status = favStatus
            if (favStatus == 1){
                img.setImageResource(R.drawable.baseline_favorite_24)
            }else{
                img.setImageResource(R.drawable.no_favorite)
            }
        }
    }
}