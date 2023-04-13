package com.example.appshoppingdatn.presentation.ui.activity.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
    }
}