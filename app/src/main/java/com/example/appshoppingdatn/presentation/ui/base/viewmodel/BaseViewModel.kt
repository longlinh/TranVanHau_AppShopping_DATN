package com.example.appshoppingdatn.presentation.ui.base.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
   var isLoading = MutableLiveData<Boolean>()
}