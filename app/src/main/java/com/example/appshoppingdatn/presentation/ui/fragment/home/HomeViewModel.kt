package com.example.appshoppingdatn.presentation.ui.fragment.home

import android.content.Context
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.New
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeViewModel : BaseViewModel() {
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null



    fun onInsertFavoriteToSQLite(news : New , context : Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Shopping.db",null,1)

    }
}