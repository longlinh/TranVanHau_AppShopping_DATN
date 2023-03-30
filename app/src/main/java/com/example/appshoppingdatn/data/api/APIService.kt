package com.example.appshoppingdatn.data.api

import com.example.appshoppingdatn.model.NewModel
import com.example.appshoppingdatn.model.SaleModel
import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {
    @GET("getspmoi.php")
    fun getSPNew() : Observable<NewModel>
    @GET("getspsale.php")
    fun getSPSale() : Observable<SaleModel>
}