package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class PaymentViewModel  :BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser?= null
    private var auth: FirebaseAuth ?=null
    var idAccount : String ?= null
    companion object{
        const val PAYMENT_SUCCESS = 1
        const val PAYMENT_FAILD = 2
    }
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
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

    fun onClickPayment(context: Context,idBill : String,nameUser : String,phoneUser:String,sumPrice : Float,edtAddress : EditText,date:String,productArrayList :ArrayList<Cart>){
        val address = edtAddress.text.toString().trim()
        if (address.isEmpty()){
            edtAddress.error = context.getString(R.string.txtMessageAddressEmpty)
        }else{
            idAccount = firebaseUser!!.uid
            databaseReference = FirebaseDatabase.getInstance().getReference("Bill").child(idAccount!!).child(idBill)
            val hashMap = HashMap<String,Any>()
            hashMap["userID"] = idAccount!!
            hashMap["idBill"] = idBill
            hashMap["nameUser"] = nameUser
            hashMap["phoneUser"] = phoneUser
            hashMap["address"] = address
            hashMap["sumPrice"] = sumPrice
            hashMap["dateOrder"] = date
            hashMap["productArrayList"] = productArrayList
            databaseReference!!.setValue(hashMap).addOnCompleteListener {
                if (it.isSuccessful){
                    uiEventLiveData.value = PAYMENT_SUCCESS
                }else{
                    uiEventLiveData.value = PAYMENT_FAILD
                }
            }

        }
    }
}