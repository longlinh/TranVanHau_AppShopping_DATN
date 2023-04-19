package com.example.appshoppingdatn.presentation.ui.fragment.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.databinding.ItemNotificationBinding
import com.example.appshoppingdatn.model.Notification

class NotificationAdapter(private val inters : INoti) : RecyclerView.Adapter<NotificationAdapter.Companion.NotiViewHolder>() {
    companion object{
        class NotiViewHolder(val binding : ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INoti{
        fun getCount() : Int
        fun getData(position : Int) : Notification
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return inters.getCount()
    }

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        val noti = inters.getData(position)
        holder.binding.txtTB.text = noti.txtTB
        holder.binding.txtDateTB.text = noti.dateTB
    }

}