package com.daniel.helloworld.pertemuan12.api

import com.daniel.helloworld.pertemuan12.response_api.ResponseDataProduct
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceProduct {

    @GET("products/search")
    suspend fun getProducts(
        @Query("q") keyword: String
    ): ResponseDataProduct
}