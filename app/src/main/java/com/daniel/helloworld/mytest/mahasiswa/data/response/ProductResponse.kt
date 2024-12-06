package com.daniel.helloworld.mytest.mahasiswa.data.response

import com.crocodic.core.api.ModelResponse
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val product : List<Product>
) : ModelResponse()