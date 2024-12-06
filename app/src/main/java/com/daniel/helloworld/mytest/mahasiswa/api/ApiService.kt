package com.daniel.helloworld.mytest.mahasiswa.api

import com.crocodic.core.api.ModelResponse
import com.daniel.helloworld.mytest.mahasiswa.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProduct(): ProductResponse

    @GET("products/search")
    suspend fun searchProduct(
        @Query("q") keywords: String
    ): ProductResponse
}