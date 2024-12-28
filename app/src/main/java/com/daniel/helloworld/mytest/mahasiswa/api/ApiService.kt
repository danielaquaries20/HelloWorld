package com.daniel.helloworld.mytest.mahasiswa.api

import com.daniel.helloworld.mytest.mahasiswa.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProduct(): ProductResponse

    @GET("products/search")
    suspend fun searchProduct(
        @Query("q") keywords: String
    ): ProductResponse

    @GET("products/category/{category}")
    suspend fun filterProduct(
        @Path("category") category: String
    ): ProductResponse

    @GET("products")
    suspend fun sortProduct(
        @Query("sortBy") sortBy: String,
        @Query("order") order: String
    ): ProductResponse

    @GET("products")
    suspend fun pagingProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("select") select: String
    ): ProductResponse

    @GET("products?limit=5&skip=90&select=title,thumbnail")
    suspend fun slider(): ProductResponse
}