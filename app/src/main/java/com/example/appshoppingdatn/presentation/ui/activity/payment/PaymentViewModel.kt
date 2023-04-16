package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.content.Context
import android.text.Editable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentViewModel  :BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser?= null

    companion object{
        const val MESSAGE_PAYMENT_SUCCESS = 1
        const val MESSAGE_PAYMENT_FAILD = 2
        const val ADDRESS_EMPTY = 3
    }
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
    }
    fun updateInfor(txtName : TextView,txtPhone : TextView){
        if (firebaseUser==null){
            return
        }else {
            val idAccount = firebaseUser!!.uid
            databaseReference = firebaseDatabase!!.getReference("User").child(idAccount)
            databaseReference!!.get().addOnSuccessListener {
                if (it.exists()){
                    val name = it.child("name").value
                    val phone = it.child("phone").value
                    txtName.text = name.toString()
                    txtPhone.text = phone.toString()
                }
            }
        }
    }
    fun onClickPayment(context: Context,edtAddress : EditText){
        val address = edtAddress.text.toString().trim()
        if (address.isEmpty()){
            uiEventLiveData.value = ADDRESS_EMPTY
        }else{

        }
    }
}