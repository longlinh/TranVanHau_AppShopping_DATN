package com.example.appshoppingdatn.presentation.ui.fragment.favorite

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Favorite
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList

class FavoriteViewModel : BaseViewModel() {
    private var sqLiteHelper: SQLiteHelper? = null
    private var firebaseUser: FirebaseUser? = null
    private var idAccount: String? = null
    val uiEventLiveData = SingleLiveData<Int>()
    var listFav : ArrayList<Favorite> ?= null

    init {
        listFav = ArrayList()
    }
    fun onShowData(context: Context){
        if (listFav != null){
            listFav!!.clear()
        }
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM FAVORITE2 WHERE IdAccount = '$idAccount' AND StatusFav = '1' ")
        while (data.moveToNext()){
            val idFav = data.getString(2)
            val imgFav = data.getString(3)
            val nameFav = data.getString(4)
            val priceNow = data.getFloat(5)
            val priceOld = data.getFloat(6)
            val dicription = data.getString(7)
            val type = data.getString(8)
            val selled = data.getInt(9)
            val checkFav = data.getString(11)
            listFav!!.add(Favorite(idFav,imgFav,nameFav,priceNow,priceOld,dicription,type,selled,checkFav))
        }
    }
    fun onRemoveFav(idFav : String , context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM FAVORITE2 WHERE IdSP = '$idFav'")
    }
    fun onRemoveItem(position : Int){
        listFav!!.removeAt(position)
    }
}