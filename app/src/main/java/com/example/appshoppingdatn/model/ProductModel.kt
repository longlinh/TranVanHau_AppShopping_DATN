package com.example.appshoppingdatn.model

data class ProductModel(
    val success : Boolean,
    val message : String,
    var result : java.util.ArrayList<Product>
)