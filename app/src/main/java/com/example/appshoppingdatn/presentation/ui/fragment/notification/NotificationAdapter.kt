package com.example.appshoppingdatn.presentation.ui.fragment.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.databinding.ItemNotificationBinding
import com.example.appshoppingdatn.model.Notification
import java.util.ArrayList

class NotificationAdapter(val listNoti : ArrayList<Notification>,val inters : INoti) : RecyclerView.Adapter<NotificationAdapter.Companion.NotiViewHolder>() {
    companion object{
        class NotiViewHolder(val binding : ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INoti{
        fun onClickRemoveNoti(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNoti.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        val noti = listNoti[position]
        holder.binding.txtTB.text = noti.txtTB
        holder.binding.txtDateTB.text = noti.dateTB

        holder.itemView.setOnLongClickListener {
            inters.onClickRemoveNoti(noti.idTB)
            true
        }
    }

}