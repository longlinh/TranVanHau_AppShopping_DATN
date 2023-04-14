package com.example.appshoppingdatn.presentation.ui.activity.cart

import android.content.Context
import android.util.Log
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CartViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null

    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
    }
    fun getDataCart(context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        if (Utils.cartArrayList != null){
            Utils.cartArrayList.clear()
        }
       val data = sqLiteHelper!!.getData("SELECT * FROM CART1 WHERE IdAccount = '$idAccount' ")
       while (data.moveToNext()){
           val id = data.getString(2)
           val img = data.getString(3)
           val name = data.getString(4)
           val price = data.getFloat(5)
           val destion = data.getString(6)
           val sell = data.getInt(7)
           val number = data.getInt(8)
           val sumprice = data.getFloat(9)
           Utils.cartArrayList.add(Cart(id,img,name,price,destion,sell,number,sumprice))
           Log.d("sumprice",sumprice.toString())
       }
    }
}