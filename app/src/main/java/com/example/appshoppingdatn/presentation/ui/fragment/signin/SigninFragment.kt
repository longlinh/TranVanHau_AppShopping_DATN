package com.example.appshoppingdatn.presentation.ui.fragment.signin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentSigninBinding
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.ultis.CustomProgressDialog

class SigninFragment : BaseFragment<FragmentSigninBinding>() {
    private var progressdialog: CustomProgressDialog? = null
    private lateinit var viewModel : SigninViewModel
    private var sharedPreferences : SharedPreferences?=null
    private var dialog : Dialog ?= null
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
                SigninViewModel.RESET_PASSWORD_SUCCESS -> onResetPasswordSuccess()
            }
        }
        progressdialog = CustomProgressDialog(context)
        initSharedPre()
        onClickVisiblePassWord()
        onSignin()
        onClickForgotPassword()
    }

    private fun onResetPasswordSuccess() {
        showMessage("Password reset successfully, please check your email !")
        dialog!!.dismiss()
    }

    private fun onClickForgotPassword() {
        binding.txtForgotPassword.setOnClickListener {
            OpenDialog(Gravity.CENTER)
            val edtEmail = dialog!!.findViewById<EditText>(R.id.edtEmailReset)
            val dialogOK = dialog!!.findViewById<Button>(R.id.btnOK)
            dialogOK.setOnClickListener {
                val strEmail = edtEmail.text.toString().trim()
                viewModel.onResetPassword(strEmail,edtEmail)
            }
            val dialogCancel = dialog!!.findViewById<Button>(R.id.btnCancle)
            dialogCancel.setOnClickListener {
                dialog!!.dismiss()
            }
        }
    }

    private fun initSharedPre() {
        sharedPreferences = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)
        binding.edtEmail.setText(sharedPreferences!!.getString("email",""))
        binding.edtPassword.setText(sharedPreferences!!.getString("password",""))
        binding.checkPass.isChecked = sharedPreferences!!.getBoolean("checked",false)
    }
    private fun onSaveUser() {
        if(binding.checkPass.isChecked){
            val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString("email",binding.edtEmail.text.toString())
            editor.putString("password",binding.edtPassword.text.toString())
            editor.putBoolean("checked",true)
            editor.apply()
        }else{
            val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.remove("email")
            editor.remove("password")
            editor.remove("checked")
            editor.apply()
        }
    }
    private fun onSignin() {
        binding.btnLogin.setOnClickListener {
            onSaveUser()
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
        progressdialog!!.dismiss()
    }

    private fun onShowDialog() {
        progressdialog!!.show()
    }

    private fun onLoginSuccess() {
        val intent = Intent(context, HomeActivity::class.java)
        context?.startActivity(intent)
    }
    private fun OpenDialog(gravity: Int) {
        dialog = Dialog(requireContext())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_reset_password)
        val window = dialog!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialog!!.setCancelable(true)
        } else {
            dialog!!.setCancelable(false)
        }
        dialog!!.show()
    }
}