package com.example.appshoppingdatn.data.api

import com.example.appshoppingdatn.model.NewModel
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.model.SaleModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("getspmoi.php")
    fun getSPNew() : Observable<NewModel>
    @GET("getspsale.php")
    fun getSPSale() : Observable<SaleModel>
    @POST("getcategory.php")
    @FormUrlEncoded
    fun getCategory(
        @Field("TypeProduct") type : Int
    ) : Observable<ProductModel>
}