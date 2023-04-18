package com.example.appshoppingdatn.presentation.ui.fragment.signin

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.presentation.ui.fragment.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SigninViewModel : BaseViewModel() {
    private var auth: FirebaseAuth?=null
    val uiEventLiveData = SingleLiveData<Int>()
    private var passwordNotVisible = 1
    private var firebaseUser : FirebaseUser?= null
    companion object{
        const val NAV_LOGIN_SUCCESS = 1
        const val DIALOG_LOGIN_SHOW = 2
        const val DIALOG_LOGIN_DIS = 3
        const val NAV_LOGIN_ERROR = 4
        const val RESET_PASSWORD_SUCCESS = 5
    }

    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        auth = FirebaseAuth.getInstance()
    }
    fun onSignin(email : String, passWord : String , edtEmail : EditText , edtPassword : EditText,context:Context){

        if (email.isEmpty()){
            showMessageValidate(edtEmail,context.getString(R.string.showEmailEmpty))
        }
        else if (!emailValidator(email)){
            showMessageValidate(edtEmail,context.getString(R.string.showValidateEmail))
        }
        else if (passWord.isEmpty()){
            showMessageValidate(edtPassword,context.getString(R.string.showValidatePassword))
        }
        else{
            uiEventLiveData.value = DIALOG_LOGIN_SHOW
            auth!!.signInWithEmailAndPassword(email, passWord).addOnCompleteListener { task ->
                uiEventLiveData.value = DIALOG_LOGIN_DIS
                if (task.isSuccessful) {
                    uiEventLiveData.value = NAV_LOGIN_SUCCESS
                } else {
                    uiEventLiveData.value = NAV_LOGIN_ERROR
                }
            }
        }
    }
    fun onResetPassword(email: String , edtEmail : EditText,context: Context){
        if (email.isEmpty()){
            showMessageValidate(edtEmail, context.getString(R.string.showEmailEmpty))
        }else if (!emailValidator(email)){
            showMessageValidate(edtEmail,context.getString(R.string.showValidateEmail))
        }else{
            uiEventLiveData.value = DIALOG_LOGIN_SHOW
            auth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    uiEventLiveData.value = DIALOG_LOGIN_DIS
                    if (task.isSuccessful) {
                       uiEventLiveData.value = RESET_PASSWORD_SUCCESS
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
    fun onCLickVisiblePassword(imgCheckPass : ImageView , edtPassword : EditText){
        if(passwordNotVisible==1){
            imgCheckPass.setImageResource(R.drawable.baseline_visibility_off_24)
            edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordNotVisible = 0
        }else{
            imgCheckPass.setImageResource(R.drawable.baseline_visibility_24 )
            edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordNotVisible = 1
        }
        edtPassword.setSelection(edtPassword.length())
    }

}