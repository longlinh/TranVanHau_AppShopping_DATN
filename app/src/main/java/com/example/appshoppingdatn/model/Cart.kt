package com.example.appshoppingdatn.model

data class Cart (
    var idCart : String ?= null,
    var imgCart : String ?= null,
    var nameCart : String ?= null,
    var priceCart : Float ?= null,
    var destionCart : String ?= null,
    var selledCart : Int ?= null,
    var numberOder : Int ?= null,
    var sumPrice : Float ?= null
)