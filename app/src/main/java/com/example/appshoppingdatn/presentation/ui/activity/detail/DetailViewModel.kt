package com.example.appshoppingdatn.presentation.ui.activity.detail

import android.content.Context
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DetailViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null

    companion object{

    }
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
    }
    fun addToCart(context: Context,idSP : String , imgCart : String , nameCart : String , priceCart : Float , destionCart : String , selledCart : Int , numberOder : Int){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        if (Utils.cartArrayList.size > 0){
            var exists = false
            for (i in 0 until Utils.cartArrayList.size) {
                if (Utils.cartArrayList[i].idCart == idSP) {
                    Utils.cartArrayList[i].numberOder = (Utils.cartArrayList[i].numberOder!! + numberOder)
                    Utils.cartArrayList[i].priceCart = priceCart * Utils.cartArrayList[i].numberOder!!
                    exists = true
                    sqLiteHelper!!.QueryData("UPDATE CART1 SET NumberOrder = '${Utils.cartArrayList[i].numberOder}' , SumPrice = '${Utils.cartArrayList[i].priceCart} ' WHERE IdSP = '$idSP' AND IdAccount = '$idAccount' ")
                }
            }
            if (!exists) {
                val sumPrice = numberOder* priceCart
                sqLiteHelper!!.QueryData("INSERT INTO CART1 VALUES(null, '$idAccount' , '$idSP' , '$imgCart','$nameCart' ,'$priceCart','$destionCart','$selledCart','$numberOder','$sumPrice')")
            }
        }else {
            val sumPrice = numberOder * priceCart
            sqLiteHelper!!.QueryData("INSERT INTO CART1 VALUES(null, '$idAccount' , '$idSP' , '$imgCart','$nameCart' ,'$priceCart','$destionCart','$selledCart','$numberOder','$sumPrice')")
        }
    }
    fun buyNow(context: Context,idSP : String , imgCart : String , nameCart : String , priceCart : Float , destionCart : String , selledCart : Int , numberOder : Int){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        if (Utils.cartArrayList.size > 0){
            var exists = false
            for (i in 0 until Utils.cartArrayList.size) {
                if (Utils.cartArrayList[i].idCart == idSP) {
                    Utils.cartArrayList[i].numberOder = Utils.cartArrayList[i].numberOder!! + numberOder
                    Utils.cartArrayList[i].priceCart = priceCart * Utils.cartArrayList[i].numberOder!!
                    exists = true
                    sqLiteHelper!!.QueryData("UPDATE CART1 SET NumberOrder = '${Utils.cartArrayList[i].numberOder}' , SumPrice = '${Utils.cartArrayList[i].priceCart} ' WHERE IdSP = '$idSP' AND IdAccount = '$idAccount' ")
                }
            }
            if (!exists) {
                val sumPrice = numberOder * priceCart
                sqLiteHelper!!.QueryData("INSERT INTO CART1 VALUES(null, '$idAccount' , '$idSP' , '$imgCart','$nameCart' ,'$priceCart','$destionCart','$selledCart','$numberOder','$sumPrice')")
            }
        }else {
            val sumPrice = numberOder * priceCart
            sqLiteHelper!!.QueryData("INSERT INTO CART1 VALUES(null, '$idAccount' , '$idSP' , '$imgCart','$nameCart' ,'$priceCart','$destionCart','$selledCart','$numberOder','$sumPrice')")
        }
    }
}