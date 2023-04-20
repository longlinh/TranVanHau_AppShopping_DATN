package com.example.appshoppingdatn.presentation.ui.fragment.notification

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentNotificationBinding
import com.example.appshoppingdatn.model.Notification
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.ultis.Utils

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() , NotificationAdapter.INoti{
    private var adapter : NotificationAdapter ?= null
    private lateinit var viewModel: NotificationViewModel
    private var dialogNoti : Dialog?= null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_notification
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
       // adapter = NotificationAdapter(viewModel.listNoti!!,this)
        adapter = NotificationAdapter(Utils.notiArrayList,this)
        initRecylerView()
        onShowDataNoti()
        onClickBack()
    }

    private fun onClickBack() {
        binding.imgBackNoti.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun initRecylerView() {
       val linearLayoutManager = LinearLayoutManager(requireActivity())
       binding.recylerNoti.layoutManager = linearLayoutManager
       binding.recylerNoti.adapter = adapter
    }

    private fun onShowDataNoti() {
        viewModel.onShowDataNoti(requireActivity())
    }



    private fun OpenDialogCamera(gravity: Int) {
        dialogNoti = Dialog(requireContext())
        dialogNoti!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogNoti!!.setContentView(R.layout.dialog_delete)
        val window = dialogNoti!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialogNoti!!.setCancelable(true)
        } else {
            dialogNoti!!.setCancelable(false)
        }
        dialogNoti!!.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClickRemoveNoti(id: Int) {
        OpenDialogCamera(Gravity.CENTER)
        val btnOk = dialogNoti!!.findViewById<Button>(R.id.btnOKDelete)
        val btnCancel = dialogNoti!!.findViewById<Button>(R.id.btnCancleDelete)
        val txtContent = dialogNoti!!.findViewById<TextView>(R.id.txtContentDialog)
        txtContent.text = requireActivity().getString(R.string.txtMessageDialogDelete)
        btnOk.setOnClickListener {
            adapter!!.notifyDataSetChanged()
            viewModel.onRemoveNoti(id,requireActivity())
            dialogNoti!!.dismiss()

        }
        btnCancel.setOnClickListener {
            dialogNoti!!.dismiss()
        }
    }
}