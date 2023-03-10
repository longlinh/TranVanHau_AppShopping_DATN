package com.example.appshoppingdatn.presentation.ui.fragment.signup

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignupViewModel : BaseViewModel(){
    lateinit var email : String
    lateinit var user : String
    lateinit var passWord : String
    lateinit var cofirmPassword : String
    lateinit var phoneNumber : String
    private var firebaseUserMutableLiveData : MutableLiveData<FirebaseUser> ?= null
    private var auth: FirebaseAuth?=null
    val uiEventLiveData = SingleLiveData<Int>()

    companion object{
        const val NAV_REGISTER_SUCCESS = 1
        const val DIALOG_REGISTER_SHOW = 2
        const val DIALOG_REGISTER_DIS = 3
        const val NAV_REGISTER_ERROR = 4
        const val SHOW_MESSAGE_ERROR_VALIDATE = 5
    }

    init {

        firebaseUserMutableLiveData = MutableLiveData()
        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser !=null){
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }
    fun onSignup(){
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(passWord)||TextUtils.isEmpty(user)||TextUtils.isEmpty(cofirmPassword)||TextUtils.isEmpty(phoneNumber)){
                uiEventLiveData.value = SHOW_MESSAGE_ERROR_VALIDATE
        }else{
            //uiEventLiveData.value = DIALOG_REGISTER_SHOW
            auth?.createUserWithEmailAndPassword(email,passWord)?.addOnSuccessListener{task->
//            uiEventLiveData.value = DIALOG_REGISTER_DIS

                    uiEventLiveData.value = NAV_REGISTER_SUCCESS
                }
                    //uiEventLiveData.value = NAV_REGISTER_ERROR

            }


    }


}