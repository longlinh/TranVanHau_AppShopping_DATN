package com.example.appshoppingdatn.presentation.ui.fragment.chat

import android.annotation.SuppressLint
import android.util.Log
import com.example.appshoppingdatn.model.User
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

class ChatViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser?= null
    var listChat : ArrayList<User> ? = null

    companion object{
        const val NOTIFY_DATACHANGE = 1
    }
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        listChat = ArrayList()
    }

    @SuppressLint("NotifyDataSetChanged")
   fun getDataFromRealTimeDatabase() {
        if (firebaseUser==null){
            return
        }else{
            val idAccount = firebaseUser!!.uid
            databaseReference = firebaseDatabase!!.getReference("User")
            databaseReference!!.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listChat!!.clear()
                    for (data : DataSnapshot in snapshot.children){
                        val user = data.getValue(User::class.java)
                        if (user!!.userID != idAccount){
                            listChat!!.add(user)
                        }
                    }
                    uiEventLiveData.value = NOTIFY_DATACHANGE
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}