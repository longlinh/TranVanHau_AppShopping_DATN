package com.example.appshoppingdatn.model

data class Sale(
    val idSale : Int,
    val imgSale : String,
    val nameSale : String,
    val priceSaleNow : Float,
    val priceSaleOld : Float,
    val discriptionSale : String,
    val typeSale : String,
    val selledSale : Int,
    val percentSale : Int,
    var fav_status : Int
    ) {

}