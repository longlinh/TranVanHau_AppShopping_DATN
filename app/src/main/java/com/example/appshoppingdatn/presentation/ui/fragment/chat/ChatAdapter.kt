package com.example.appshoppingdatn.presentation.ui.fragment.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.databinding.ItemUserChatBinding
import com.example.appshoppingdatn.model.User

class ChatAdapter(val context: Context,val chatList : java.util.ArrayList<User>) : RecyclerView.Adapter<ChatAdapter.Companion.ChatViewHolder>(){
    companion object{
        class ChatViewHolder(var binding : ItemUserChatBinding) : RecyclerView.ViewHolder(binding.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemUserChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val data = chatList[position]
        holder.binding.txtUserName.text = data.name

        if (data.message == ""){
            holder.binding.txtMessge.visibility = View.GONE
        }else{
            holder.binding.txtMessge.text = data.message
            holder.binding.txtMessge.visibility = View.VISIBLE
        }

      //  Glide.with(context).load(data.avatar).error(R.drawable.load_img).into(holder.binding.imgAvatar)
    }

}