package com.example.appshoppingdatn.presentation.ui.fragment.notification

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentNotificationBinding
import com.example.appshoppingdatn.model.Notification
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() , NotificationAdapter.INoti{
    override fun getLayoutResId(): Int {
        return R.layout.fragment_notification
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getData(position: Int): Notification {
        TODO("Not yet implemented")
    }
}