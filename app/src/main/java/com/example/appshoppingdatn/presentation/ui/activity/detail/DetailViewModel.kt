package com.example.appshoppingdatn.presentation.ui.activity.detail

import android.content.Context
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
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
        sqLiteHelper!!.QueryData("INSERT INTO CART VALUES(null, '$idAccount' , '$idSP' , '$imgCart','$nameCart' ,'$priceCart','$destionCart','$selledCart','$numberOder')")
    }
}