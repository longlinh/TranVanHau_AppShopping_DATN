package com.example.appshoppingdatn.presentation.ui.activity.message

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityMessageBinding
import com.example.appshoppingdatn.model.Chat
import com.example.appshoppingdatn.model.User
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

class MessageActivity : BaseActivity<ActivityMessageBinding>() {
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()
    private var idAccount: String? = null
    var topic = ""

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_message
    }

    override fun initControls(savedInstanceState: Bundle?) {
        getDataIntent()
        onClickBack()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        chatList = ArrayList()
        idAccount = firebaseUser!!.uid
        reference = FirebaseDatabase.getInstance().getReference("User").child(idAccount!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(User::class.java)
                mBinding.txtNameMessage.text = user!!.name
                if (user.avataURL == "") {
                    mBinding.imageMessage.setImageResource(R.drawable.ic_user_name)
                } else {
                    Glide.with(this@MessageActivity).load(user.avataURL).into(mBinding.imageMessage)
                }
            }
        })
        mBinding.btnSend.setOnClickListener {
            val message = mBinding.edtMessageSend.text.toString().trim()
            if (message.isEmpty()){
                showMessage(getString(R.string.messageEmpty))
                mBinding.edtMessageSend.setText("")
            }else{
//                sendMessage(idAccount!!)
            }
        }
    }

    private fun onClickBack() {
        mBinding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun getDataIntent() {
        val intent = intent
        val avatar = intent.getStringExtra("img")
        val userName = intent.getStringExtra("name")
        Glide.with(applicationContext).load(avatar).error(R.drawable.load_img).into(mBinding.imageMessage)
        mBinding.txtNameMessage.text = userName
    }
    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        var hashMap: HashMap<String, String> = HashMap()
        hashMap["senderId"] = senderId
        hashMap["receiverId"] = receiverId
        hashMap["message"] = message

        reference!!.child("Chat").push().setValue(hashMap)

    }
}