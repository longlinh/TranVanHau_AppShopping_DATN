package com.example.appshoppingdatn.presentation.ui.fragment.chat


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentChatBinding
import com.example.appshoppingdatn.model.User
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import java.util.ArrayList

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    private var chatAdapter : ChatAdapter ?= null
    private var viewModel: ChatViewModel ?= null
    private var listUser: ArrayList<User> = ArrayList()
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
        viewModel!!.listChat.observe(viewLifecycleOwner, Observer {
            listUser.clear()
            listUser.addAll(it)
            onDataSetChange()
        })
        initRecylerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onDataSetChange() {
        chatAdapter!!.notifyDataSetChanged()
    }

    private fun initRecylerView() {
        viewModel!!.getDataFromRealTimeDatabase()
        chatAdapter = ChatAdapter(requireActivity(),listUser)
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recylerChat.layoutManager = linearLayoutManager
        binding.recylerChat.adapter = chatAdapter
    }

}