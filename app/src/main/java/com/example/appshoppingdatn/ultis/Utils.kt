package com.example.appshoppingdatn.ultis



import com.example.appshoppingdatn.model.NewModel
import com.example.appshoppingdatn.model.Product
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.model.SaleModel
import java.util.ArrayList

class Utils {
    companion object{
        var listSalesMode :SaleModel ?= null
        var listNewsModel  : NewModel ?= null
        var listProductModel : ProductModel ?= null
//        var type = ""
        var listPhone : ArrayList<Product> ?= null
        var listWatch : ArrayList<Product> ?= null
        var listLaptop : ArrayList<Product> ?= null
    }

}