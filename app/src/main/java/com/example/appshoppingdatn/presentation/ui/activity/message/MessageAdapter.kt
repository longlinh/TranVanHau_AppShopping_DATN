package com.example.appshoppingdatn.presentation.ui.activity.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageAdapter(private val context: Context,private val chatList : java.util.ArrayList<Chat>) : RecyclerView.Adapter<MessageAdapter.Companion.MessageViewHolder>() {
    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser: FirebaseUser? = null
    companion object{
        class MessageViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val txtMessage: TextView = view.findViewById(R.id.tvMessage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_right,parent,false)
            return MessageViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_left,parent,false)
            return MessageViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val chat = chatList[position]
        holder.txtMessage.text = chat.message
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (chatList[position].senderId == firebaseUser!!.uid){
            return MESSAGE_TYPE_RIGHT
        }else{
            return MESSAGE_TYPE_LEFT
        }
    }
}