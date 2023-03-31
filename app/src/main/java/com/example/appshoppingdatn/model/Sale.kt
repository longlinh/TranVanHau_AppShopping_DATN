package com.example.appshoppingdatn.model

data class Sale(
    val IdSale : Int,
    val ImageSale : String,
    val NameSale : String,
    val PriceSaleNow : Float,
    val PriceSaleOld : Float,
    val DiscriptionSale : String,
    val TypeSale : String,
    val SelledSale : Int,
    val PercentSale : Int,
    var FavStatusSale : Int
)