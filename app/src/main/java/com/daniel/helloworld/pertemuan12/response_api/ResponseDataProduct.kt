package com.daniel.helloworld.pertemuan12.response_api

import com.crocodic.core.api.ModelResponse
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.google.gson.annotations.SerializedName

data class ResponseDataProduct(
    @SerializedName("products")
    val products: List<DataProduct>
) : ModelResponse()
