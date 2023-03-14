package com.example.appshoppingdatn.presentation.ui.fragment.chat

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentChatBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_chat
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }
}