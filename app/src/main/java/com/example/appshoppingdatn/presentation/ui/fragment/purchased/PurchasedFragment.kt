package com.example.appshoppingdatn.presentation.ui.fragment.purchased

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentPurchasedBinding
import com.example.appshoppingdatn.model.Bill
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class PurchasedFragment : BaseFragment<FragmentPurchasedBinding>() {
    private lateinit var purchasedArrayList : ArrayList<Bill>
    private lateinit var purchasedAdapter: PurchasedAdapter
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser?= null
    override fun getLayoutResId(): Int {
       return R.layout.fragment_purchased
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        purchasedArrayList = ArrayList()
        initDataPurchased()
        onClickBack()
    }

    private fun onClickBack() {
        binding.imgBackPurchased.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initDataPurchased() {
        getDataFromRealTimeDatabase()
        purchasedAdapter = PurchasedAdapter(requireActivity(),purchasedArrayList)
        val linearLayoutManager = GridLayoutManager(context,1,RecyclerView.HORIZONTAL,false)
        binding.recylerPurchasedOrder.layoutManager = linearLayoutManager
        binding.recylerPurchasedOrder.adapter = purchasedAdapter
    }

    private fun getDataFromRealTimeDatabase() {
        if (firebaseUser==null){
            return
        }else{
            val idAccount = firebaseUser!!.uid
            databaseReference = firebaseDatabase!!.getReference("Bill").child(idAccount)
            databaseReference!!.get().addOnSuccessListener {
                if (it.exists() && it.hasChildren()){
                    for (data in it.children){
                        val bill = data.getValue(Bill::class.java) as Bill
                        purchasedArrayList.add(bill)
                        Log.d("list",purchasedArrayList.toString())
                    }
                    purchasedAdapter.notifyDataSetChanged()

                }
            }
        }
    }
}