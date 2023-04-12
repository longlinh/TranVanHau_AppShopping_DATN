package com.example.appshoppingdatn.presentation.ui.fragment.signup


import android.content.Context
import android.widget.EditText
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.model.User
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupViewModel : BaseViewModel(){
    private var auth: FirebaseAuth ?=null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    val uiEventLiveData = SingleLiveData<Int>()
    var idAccount : String ?= null

    companion object{
        const val NAV_REGISTER_SUCCESS = 1
        const val DIALOG_REGISTER_SHOW = 2
        const val DIALOG_REGISTER_DIS = 3
        const val NAV_REGISTER_ERROR = 4
    }

    init {
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()


    }
    fun onSignup(email : String,name : String,passWord : String,cofirmPassword : String,phoneNumber : String,
    edtEmail : EditText , edtUser : EditText , edtPassword : EditText , edtConfirmPass : EditText , edtPhone : EditText,context: Context){
        if (email.isEmpty()){
            showMessageValidate(edtEmail,context.getString(R.string.txtEmailEmpty))
        }
        else if (!emailValidator(email)){
            showMessageValidate(edtEmail,context.getString(R.string.txtValidateEmail))
        }
        else if (name.isEmpty()){
            showMessageValidate(edtUser,context.getString(R.string.txtUserEmpty))
        }
        else if (passWord.isEmpty()){
            showMessageValidate(edtPassword,context.getString(R.string.txtPasswordEmpty))
        }
        else if (passWord.length < 6){
            showMessageValidate(edtPassword,context.getString(R.string.txtPasswordLength))
        }
        else if (cofirmPassword.isEmpty()){
            showMessageValidate(edtConfirmPass,context.getString(R.string.txtValidatePassword))
        }
        else if (phoneNumber.isEmpty()){
            showMessageValidate(edtPhone,context.getString(R.string.txtPhoneEmpty))
        }
        else if (cofirmPassword != passWord){
            showMessageValidate(edtConfirmPass,context.getString(R.string.txtPasswordWrong))
        }
        else{
            uiEventLiveData.value = DIALOG_REGISTER_SHOW
            auth?.createUserWithEmailAndPassword(email, passWord)?.addOnCompleteListener { task ->
                uiEventLiveData.value = DIALOG_REGISTER_DIS
                if (task.isSuccessful) {
                    idAccount = task.result.user!!.uid
                    //add data to firebase database
                    databaseReference = FirebaseDatabase.getInstance().getReference("User").child(idAccount!!)
                    val hashMap = HashMap<String, String>()
                    hashMap["userID"] = idAccount!!
                    hashMap["name"] = name
                    hashMap["email"] = email
                    hashMap["password"] = passWord
                    hashMap["phone"] = phoneNumber
                    databaseReference!!.setValue(hashMap).addOnCompleteListener {
                        if (task.isSuccessful){
                            uiEventLiveData.value = NAV_REGISTER_SUCCESS
                        }
                    }
                } else {
                    uiEventLiveData.value = NAV_REGISTER_ERROR
                }
            }
        }
    }
    private fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
    private fun showMessageValidate(edt : EditText,messageError : String){
        edt.error = messageError
    }
}