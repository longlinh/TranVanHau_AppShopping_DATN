package com.example.appshoppingdatn.model

data class ProductModel(
    val success : Boolean,
    val message : String,
    val result : List<Product>
)