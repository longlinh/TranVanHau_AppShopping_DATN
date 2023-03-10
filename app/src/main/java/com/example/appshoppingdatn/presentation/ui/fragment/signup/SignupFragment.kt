package com.example.appshoppingdatn.presentation.ui.fragment.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.databinding.FragmentSignupBinding
import com.example.appshoppingdatn.presentation.ui.activity.home.MainActivity

class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    private lateinit var viewModel : SignupViewModel
    override fun getLayoutResId(): Int {
        return R.layout.fragment_signup
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                SignupViewModel.NAV_REGISTER_SUCCESS -> onRegisterSuccess()
            }
        }
        onSignup()
    }

    private fun onRegisterSuccess() {
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)
        showMessage("Signup Success")
    }

    private fun onSignup() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val user = binding.edtUser.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassword.text.toString().trim()
            val passWord = binding.edtPassword.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            viewModel.email = email
            viewModel.passWord = passWord
            viewModel.cofirmPassword = confirmPassword
            viewModel.user = user
            viewModel.phoneNumber = phone
            viewModel.onSignup()
        }

    }
}