package com.example.appshoppingdatn.presentation.ui.fragment.signup

import android.widget.EditText
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupViewModel : BaseViewModel(){
    private var auth: FirebaseAuth ?=null
    val uiEventLiveData = SingleLiveData<Int>()

    companion object{
        const val NAV_REGISTER_SUCCESS = 1
        const val DIALOG_REGISTER_SHOW = 2
        const val DIALOG_REGISTER_DIS = 3
        const val NAV_REGISTER_ERROR = 4
    }

    init {
        auth = FirebaseAuth.getInstance()
    }
    fun onSignup(email : String,user : String,passWord : String,cofirmPassword : String,phoneNumber : String,
    edtEmail : EditText , edtUser : EditText , edtPassword : EditText , edtConfirmPass : EditText , edtPhone : EditText){
        if (email.isEmpty()){
            showMessageValidate(edtEmail,"Email is not empty !")
        }
        else if (!emailValidator(email)){
            showMessageValidate(edtEmail,"Invalid email !")
        }
        else if (user.isEmpty()){
            showMessageValidate(edtUser,"User is not empty !")
        }
        else if (passWord.isEmpty()){
            showMessageValidate(edtPassword,"Password is not empty !")
        }
        else if (passWord.length < 6){
            showMessageValidate(edtPassword,"Password must be 6 or more characters !")
        }
        else if (cofirmPassword.isEmpty()){
            showMessageValidate(edtConfirmPass,"Invalid password !")
        }
        else if (phoneNumber.isEmpty()){
            showMessageValidate(edtPhone,"Phone number is not empty !")
        }
        else if (cofirmPassword != passWord){
            showMessageValidate(edtConfirmPass,"Password was wrong !")
        }
        else{
            uiEventLiveData.value = DIALOG_REGISTER_SHOW
            auth?.createUserWithEmailAndPassword(email, passWord)?.addOnCompleteListener { task ->
                uiEventLiveData.value = DIALOG_REGISTER_DIS
                if (task.isSuccessful) {
                    uiEventLiveData.value = NAV_REGISTER_SUCCESS
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