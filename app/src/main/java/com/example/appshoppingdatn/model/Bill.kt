package com.example.appshoppingdatn.model

data class Bill (
    val idBill : String ?= null,
    val nameUser : String ?= null,
    val phoneUser : String ?= null,
    val address : String ?= null,
    val sumPrice : Float ?= null,
    val dateOrder : String ?= null,
    val productArrayList : java.util.ArrayList<Cart> ?= null
)
