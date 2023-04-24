package com.example.appshoppingdatn.presentation.ui.fragment.chat


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentChatBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    private var chatAdapter : ChatAdapter ?= null
    private var viewModel: ChatViewModel ?= null
    override fun getLayoutResId(): Int {
        return R.layout.fragment_chat
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        viewModel!!.uiEventLiveData.observe(this){
            when(it){
                ChatViewModel.NOTIFY_DATACHANGE -> onDataSetChange()
            }
        }
        initRecylerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onDataSetChange() {
        chatAdapter!!.notifyDataSetChanged()
    }

    private fun initRecylerView() {
        viewModel!!.getDataFromRealTimeDatabase()
        chatAdapter = ChatAdapter(requireActivity(), viewModel!!.listChat!!)
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recylerChat.layoutManager = linearLayoutManager
        binding.recylerChat.adapter = chatAdapter
    }

}