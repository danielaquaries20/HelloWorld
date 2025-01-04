package com.daniel.helloworld.mytest.mahasiswa.data.response

import com.crocodic.core.api.ModelResponse
import com.daniel.helloworld.mytest.mahasiswa.data.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val user: User,
    @SerializedName("token")
    val token: String?
) : ModelResponse()
