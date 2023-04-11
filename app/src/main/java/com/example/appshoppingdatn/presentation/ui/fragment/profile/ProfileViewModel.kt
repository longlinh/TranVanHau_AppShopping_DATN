package com.example.appshoppingdatn.presentation.ui.fragment.profile

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        const val SHOW_PROGRESS_DIALOG = 3
        const val DISS_PROGRESS_DIALOG = 4
        const val CHANGE_SUCCESS = 5
        const val CHANGE_FAILD = 6
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

    fun updateData(edtName : EditText , edtPhone : EditText){
        val idAccount = firebaseUser!!.uid
        databaseReference = firebaseDatabase!!.getReference("User")
        val user = mapOf<String,String>(
            "name" to edtName.text.toString(),
            "phone" to edtPhone.text.toString()
        )
        databaseReference!!.child(idAccount).updateChildren(user).addOnSuccessListener {
            uiEventLiveData.value = UPDATE_SUCCESS
            edtName.text.clear()
            edtPhone.text.clear()
        }.addOnFailureListener {
            uiEventLiveData.value = UPDATE_FAILD
        }
    }
    fun changePasswordToRealTimeDb(edtPass : EditText){
        val idAccount = firebaseUser!!.uid
        databaseReference = firebaseDatabase!!.getReference("User")
        val user = mapOf<String,String>(
            "password" to edtPass.text.toString()
        )
        databaseReference!!.child(idAccount).updateChildren(user).addOnSuccessListener {
           uiEventLiveData.value = CHANGE_SUCCESS
        }.addOnFailureListener {
           uiEventLiveData.value = CHANGE_FAILD
        }
    }
    fun changePassword(edtOldPass : EditText , edtNewPass : EditText , edtConfirmPass : EditText,context: Context){
        val oldPass = edtOldPass.text.toString().trim()
        val newPass = edtNewPass.text.toString().trim()
        val confimPass = edtConfirmPass.text.toString().trim()

        if (TextUtils.isEmpty(oldPass)){
            edtOldPass.error = "Old Password can't be empty"
            edtOldPass.requestFocus()
        }else if (TextUtils.isEmpty(newPass)){
            edtNewPass.error = "New Password can't be empty"
            edtNewPass.requestFocus()
        }else if (TextUtils.isEmpty(confimPass)){
            edtConfirmPass.error = "New Password can't be empty"
            edtConfirmPass.requestFocus()
        }else if (oldPass != confimPass){
            Toast.makeText(context, "Password was wrong !", Toast.LENGTH_SHORT).show()
        }else{
            uiEventLiveData.value = SHOW_PROGRESS_DIALOG
            firebaseUser!!.updatePassword(newPass).addOnCompleteListener {
                uiEventLiveData.value = DISS_PROGRESS_DIALOG
                if (it.isSuccessful){
//                    changePasswordToRealTimeDb(edtNewPass)
                }
            }
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