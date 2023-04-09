package com.example.appshoppingdatn.presentation.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentProfileBinding
import com.example.appshoppingdatn.presentation.ui.activity.login.LoginActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var viewModel : ProfileViewModel
    override fun getLayoutResId(): Int {
        return R.layout.fragment_profile
    }
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        onClickLogout()
        onSetInformationProfile()
        onCLickVisiblitiPassword()
    }

    private fun onCLickVisiblitiPassword() {
        binding.imgShowPass.setOnClickListener {
            viewModel.onCLickVisiblePassword(binding.imgShowPass,binding.txtPassword)
        }

    }

    private fun onSetInformationProfile() {
        viewModel.updateInfor(binding.txtemail,binding.txtName,binding.txtPassword,binding.txtPhone)
    }

    private fun onClickLogout() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }
}