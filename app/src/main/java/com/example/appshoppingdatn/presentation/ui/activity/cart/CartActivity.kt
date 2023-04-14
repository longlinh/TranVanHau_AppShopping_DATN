package com.example.appshoppingdatn.presentation.ui.activity.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.ActivityCartBinding
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.model.SpinnerData
import com.example.appshoppingdatn.presentation.ui.activity.cart.spinner.CustomSpinner
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity
import com.example.appshoppingdatn.ultis.Utils
import java.text.DecimalFormat

class CartActivity : BaseActivity<ActivityCartBinding>() , CartAdapter.ICart{
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var discout = 0f
    private var feeShip =0f
    var itemTotal = 0f
    var total = 0f
    var totalDis = 0f
    val decimalFormat = DecimalFormat("###,###,###")
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_cart
    }

    override fun initControls(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){

            }
        }
        getDataFromDb()
        initRecylerview()
        checkData()
        onClickBack()
        customDataTotal()
        onSetDataSprinerDiscount()

    }

    private fun checkData() {
        if (Utils.cartArrayList.size == 0){
            mBinding.txtCartEmpty.visibility = View.VISIBLE
            mBinding.layout1.visibility = View.GONE
        }else{
            feeShip = 20000f
            mBinding.layout1.visibility = View.VISIBLE
            mBinding.txtCartEmpty.visibility = View.GONE
        }
    }

    private fun customDataTotal() {
        for (i in 0 until Utils.cartArrayList.size){
            itemTotal += Utils.cartArrayList[i].sumPrice
        }
        total = itemTotal + feeShip - discout
        totalDis = total
        mBinding.txtItemTotal.text = decimalFormat.format(itemTotal)+"đ"
        mBinding.txtTotal.text = decimalFormat.format(total)+"đ"
        mBinding.txtDelivery.text = decimalFormat.format(feeShip)+"đ"
    }

    @SuppressLint("SetTextI18n")
    private fun onSetDataSprinerDiscount() {
        val listSpiner = mutableListOf<SpinnerData>()
        if (total >= 50000000f){
            listSpiner.add(SpinnerData(R.drawable.discount, getString(R.string.txtdiscount5)))
            listSpiner.add(SpinnerData(R.drawable.voucher , getString(R.string.txtdiscount1m)))
            listSpiner.add(SpinnerData(R.drawable.voucher , getString(R.string.txtdiscount500)))
        }else{
            listSpiner.add(SpinnerData(R.drawable.voucher, getString(R.string.txtdiscount30)))
            listSpiner.add(SpinnerData(R.drawable.voucher, getString(R.string.txtdiscount15)))
            listSpiner.add(SpinnerData(R.drawable.freeshipping, getString(R.string.txtdiscountFreeShip)))
        }
        val customSpiner = CustomSpinner(this, listSpiner)
        mBinding.sprinnerDiscount.adapter = customSpiner

        mBinding.sprinnerDiscount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    val txtdiscout = listSpiner[position].txtVoucher
                    if (txtdiscout == getString(R.string.txtdiscount30)) {
                        discout = 30000f
                    } else if (txtdiscout == getString(R.string.txtdiscount5)) {
                        discout = itemTotal * 0.05f
                    } else if (txtdiscout == getString(R.string.txtdiscountFreeShip)) {
                        discout = feeShip!!
                    }else if (txtdiscout == getString(R.string.txtdiscount15)){
                        discout = 15000f
                    }else if (txtdiscout == getString(R.string.txtdiscount500)){
                        discout = 500000f
                    }else if (txtdiscout == getString(R.string.txtdiscount1m)){
                        discout = 1000000f
                    }
                    val decimalFormat = DecimalFormat("###,###,###")
                    mBinding.txtDiscountCode.text = "-"+decimalFormat.format(discout) + "đ"
                    totalDis = itemTotal + feeShip - discout
                    Log.d("onItemSelected: ", "${itemTotal.toString()} - $feeShip - $discout")
                    mBinding.txtTotal.text = decimalFormat.format(totalDis)+"đ"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Xử lý khi không có mục nào được chọn ở đây
                }
            }

    }
    private fun onClickBack() {
        mBinding.imgBack.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDataFromDb() {
            initRecylerview()
            viewModel.getDataCart(this)
    }

    private fun initRecylerview() {
        cartAdapter = CartAdapter(this)
        mBinding.recylerCart.layoutManager = LinearLayoutManager(this)
        mBinding.recylerCart.adapter = cartAdapter
    }

    override fun getCount(): Int {
       return Utils.cartArrayList.size
    }

    override fun getData(position: Int): Cart {
       return Utils.cartArrayList[position]
    }

    override fun getContext(): Context {
        return applicationContext
    }
}