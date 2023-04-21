package com.example.appshoppingdatn.presentation.ui.fragment.notification

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Notification
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList

class NotificationViewModel : BaseViewModel(){
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
    }
    @SuppressLint("NotifyDataSetChanged")
    fun onShowDataNoti(context: Context){
        if (Utils.notiArrayList != null){
            Utils.notiArrayList.clear()
        }
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        val data = sqLiteHelper!!.getData("SELECT * FROM NOTIFICATION WHERE IdAccount = '$idAccount' ORDER BY Id DESC")
        while (data.moveToNext()) {
            val idTB = data.getInt(0)
            val txtTB = data.getString(2)
            val dateTB = data.getString(3)
            Utils.notiArrayList.add(Notification(idTB, txtTB, dateTB))
        }
    }
    fun onRemoveNoti(idTB : Int , context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM NOTIFICATION WHERE Id = '$idTB' AND IdAccount = '$idAccount' ")
        onShowDataNoti(context)
    }

}