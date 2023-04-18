package com.example.appshoppingdatn.presentation.ui.activity.payment

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.model.Cart
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class PaymentViewModel  :BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser : FirebaseUser?= null
    private var auth: FirebaseAuth ?=null
    var idAccount : String ?= null
    private var SECRET_KEY = "sk_test_51LKdcILCmPlhYQx0pI3RRTHauYBPRGz1mGHuEg5F18R03Q7F30DDhyDBwh5CkKaP29oeDtXRdgdkjhffl88L1d5c00zd0YkIC5"
    private var customerID: String = ""
    private var ephericalKey: String = ""
    private var clientSecret: String = ""
    private var sqLiteHelper: SQLiteHelper? = null
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
    }
    fun updateInfor(txtName : TextView,txtPhone : TextView){
        if (firebaseUser==null){
            return
        }else {
            val idAccount = firebaseUser!!.uid
            databaseReference = firebaseDatabase!!.getReference("User").child(idAccount)
            databaseReference!!.get().addOnSuccessListener {
                if (it.exists()){
                    val name = it.child("name").value
                    val phone = it.child("phone").value
                    txtName.text = name.toString()
                    txtPhone.text = phone.toString()
                }
            }
        }
    }

    fun onClickPayment(context: Context,idBill : String,nameUser : String,phoneUser:String,sumPrice : Float,edtAddress : EditText,date:String,productArrayList :ArrayList<Cart>){
        val address = edtAddress.text.toString().trim()
        if (address.isEmpty()){
            edtAddress.error = context.getString(R.string.txtMessageAddressEmpty)
        }else{
            idAccount = firebaseUser!!.uid
            databaseReference = FirebaseDatabase.getInstance().getReference("Bill").child(idAccount!!).child(idBill)
            val hashMap = HashMap<String,Any>()
            hashMap["userID"] = idAccount!!
            hashMap["idBill"] = idBill
            hashMap["nameUser"] = nameUser
            hashMap["phoneUser"] = phoneUser
            hashMap["address"] = address
            hashMap["sumPrice"] = sumPrice
            hashMap["dateOrder"] = date
            hashMap["productArrayList"] = productArrayList
            databaseReference!!.setValue(hashMap)

        }
    }

    fun onPayment(context: Context,pricePayment : Int) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/customers",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    customerID = `object`.getString("id")
                    getPhericalKey(context,customerID,pricePayment)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                return header
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    private fun getPhericalKey(context: Context,customerID: String,pricePayment : Int) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    ephericalKey = `object`.getString("id")
                    getClientSecret(context,customerID,pricePayment)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = java.util.HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                header["Stripe-Version"] = "2020-08-27"
                return header
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["customer"] = customerID
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
    private fun getClientSecret(context: Context,customerID: String,pricePayment: Int) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/payment_intents",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    clientSecret = `object`.getString("client_secret")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = java.util.HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                return header
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["customer"] = customerID
                params["amount"] = "$pricePayment"+ "00" //money paypal
                params["currency"] = "usd"
                params["automatic_payment_methods[enabled]"] = "true"
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
    fun getCustomerID() : String{
        return customerID
    }
    fun getEphericalKey() : String{
        return ephericalKey
    }
    fun getClientSecret() : String{
        return clientSecret
    }
    fun deleteCart(context: Context){
        sqLiteHelper = SQLiteHelper(context,"Shopping1.db",null,2)
        sqLiteHelper!!.QueryData("DELETE FROM CART1 WHERE IdAccount = '$idAccount' ")
    }
}