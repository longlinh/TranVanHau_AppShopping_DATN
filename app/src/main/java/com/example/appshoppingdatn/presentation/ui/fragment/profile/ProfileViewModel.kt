package com.example.appshoppingdatn.presentation.ui.fragment.profile

import android.content.Context
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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


class ProfileViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser ?= null
    private var passwordNotVisible = 1

    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
    }
    companion object{
        const val UPDATE_SUCCESS = 1
        const val UPDATE_FAILD = 2
    }
    fun updateInfor(txtEmail : TextView , txtName : TextView,txtPassword : EditText,txtPhone :TextView,context: Context,imgAvatar : ImageView){

        if (firebaseUser==null){
            return
        }else {
            // Read from the database
            val idAccount = firebaseUser!!.uid
            databaseReference = firebaseDatabase!!.getReference("User").child(idAccount)
            databaseReference!!.get().addOnSuccessListener {
                if (it.exists()){
                    val name = it.child("name").value
                    val email = it.child("email").value
                    val phone = it.child("phone").value
                    val password = it.child("password").value
                    txtEmail.text = email.toString()
                    txtName.text = name.toString()
                    txtPassword.text = Editable.Factory.getInstance().newEditable(password.toString())
                    txtPhone.text = phone.toString()
                }
            }
            val photoUrl = firebaseUser!!.photoUrl
            Glide.with(context).load(photoUrl).error(R.drawable.avatar).into(imgAvatar)
        }
    }
    fun updateData(email : String , name : String , phone : String,edtEmail : EditText , edtName : EditText , edtPhone : EditText){
        val idAccount = firebaseUser!!.uid
        databaseReference = firebaseDatabase!!.getReference("User")
        val user = mapOf<String,String>(
            "name" to name,
            "email" to email,
            "phone" to phone
        )
        databaseReference!!.child(idAccount).updateChildren(user).addOnSuccessListener {
            edtEmail.text.clear()
            edtName.text.clear()
            edtPhone.text.clear()
            uiEventLiveData.value = UPDATE_SUCCESS
        }.addOnFailureListener {
            uiEventLiveData.value = UPDATE_FAILD
        }
    }
    fun onCLickVisiblePassword(imgCheckPass : ImageView, txtPassword : EditText){
        if(passwordNotVisible==1){
            imgCheckPass.setImageResource(R.drawable.ic_visibility_off_pass)
            txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordNotVisible = 0
        }else{
            imgCheckPass.setImageResource(R.drawable.ic_visibliti_pass )
            txtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordNotVisible = 1
        }
        txtPassword.setSelection(txtPassword.length())
    }
}