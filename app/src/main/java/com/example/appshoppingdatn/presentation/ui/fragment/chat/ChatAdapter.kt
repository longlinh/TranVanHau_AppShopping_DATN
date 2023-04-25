package com.example.appshoppingdatn.presentation.ui.fragment.chat

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ItemUserChatBinding
import com.example.appshoppingdatn.model.User
import com.example.appshoppingdatn.presentation.ui.activity.message.MessageActivity

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
        Log.d("avatar",data.avatar)
        Glide.with(context).load(data.avatar).error(R.drawable.load_img).into(holder.binding.imgAvatar)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,MessageActivity::class.java)
            intent.putExtra("userID",data.userID)
            intent.putExtra("img",data.avatar)
            intent.putExtra("name",data.name)
            context.startActivity(intent)
        }
    }

}