package com.example.appshoppingdatn.model

data class Favorite(
    val idFav : Int,
    val imgFav : String,
    val nameFav : String,
    val priceFavNow : Float,
    val priceFavOld : Float,
    val discriptionFav : String,
    val typeFav : String,
    val selledFav : Int,
    val checkFav : String
) {
}