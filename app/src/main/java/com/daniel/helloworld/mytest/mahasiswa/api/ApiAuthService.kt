package com.daniel.helloworld.mytest.mahasiswa.api

import com.daniel.helloworld.mytest.mahasiswa.data.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiAuthService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): UserResponse

}