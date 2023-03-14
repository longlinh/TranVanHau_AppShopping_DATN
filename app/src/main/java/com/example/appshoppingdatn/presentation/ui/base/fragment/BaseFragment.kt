package com.example.appshoppingdatn.presentation.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.appshoppingdatn.R

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls(view, savedInstanceState)
    }
    fun showMessage(message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    open fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment, Fragment::class.java.name)
            .commit()
    }
    abstract fun getLayoutResId(): Int

    abstract fun initControls(view: View, savedInstanceState: Bundle?)
}