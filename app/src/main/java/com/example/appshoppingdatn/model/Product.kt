package com.example.appshoppingdatn.model

data class Product(
    val IdProduct : String,
    val ImageProduct : String,
    val NameProduct : String,
    val PriceProduct : Float,
    val DescriptionProduct : String,
    val TypeProduct : String,
    val SelledProduct : Int,
    var FavStatusProduct : Int
)