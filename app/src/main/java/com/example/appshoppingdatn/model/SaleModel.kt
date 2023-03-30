package com.example.appshoppingdatn.model

data class SaleModel (
    val success : Boolean,
    val message : String,
    val result : List<Sale>
)