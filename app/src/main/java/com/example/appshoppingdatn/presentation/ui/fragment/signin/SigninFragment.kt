package com.example.appshoppingdatn.presentation.ui.fragment.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.databinding.FragmentSigninBinding
import com.example.appshoppingdatn.presentation.ui.activity.home.MainActivity
import com.example.appshoppingdatn.ultis.CustomProgressDialog

class SigninFragment : BaseFragment<FragmentSigninBinding>() {
    private var dialog: CustomProgressDialog? = null
    private lateinit var viewModel : SigninViewModel
    override fun getLayoutResId(): Int {
        return R.layout.fragment_signin
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SigninViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                SigninViewModel.NAV_LOGIN_SUCCESS -> onLoginSuccess()
                SigninViewModel.DIALOG_LOGIN_SHOW -> onShowDialog()
                SigninViewModel.DIALOG_LOGIN_DIS -> onDisDialog()
                SigninViewModel.NAV_LOGIN_ERROR -> onLoginError()

            }
        }
        dialog = CustomProgressDialog(context)
        onClickVisiblePassWord()
        onSignin()
    }

    private fun onSignin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            viewModel.onSignin(email,password,binding.edtEmail,binding.edtPassword)
        }
    }

    private fun onClickVisiblePassWord() {
        binding.imgCheckPass.setOnClickListener {
            viewModel.onCLickVisiblePassword(binding.imgCheckPass,binding.edtPassword)
        }
    }

    private fun onLoginError() {
        showMessage("Invalid login account !")
    }

    private fun onDisDialog() {
        dialog!!.dismiss()
    }

    private fun onShowDialog() {
        dialog!!.show()
    }

    private fun onLoginSuccess() {
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)
    }
}