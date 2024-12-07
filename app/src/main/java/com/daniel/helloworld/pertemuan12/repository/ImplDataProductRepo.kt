package com.daniel.helloworld.pertemuan12.repository

import com.crocodic.core.api.ApiObserver
import com.daniel.helloworld.pertemuan12.api.ApiServiceProduct
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.daniel.helloworld.pertemuan12.response_api.ResponseDataProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImplDataProductRepo @Inject constructor(private val apiServiceProduct: ApiServiceProduct) :
    DataProductsRepo {

    override fun getProducts(keyword: String): Flow<List<DataProduct>> = flow {
        ApiObserver.run(
            { apiServiceProduct.getProducts(keyword) },
            false,
            object : ApiObserver.ModelResponseListener<ResponseDataProduct> {
                override suspend fun onSuccess(response: ResponseDataProduct) {
                    emit(response.products)
                }

                override suspend fun onError(response: ResponseDataProduct) {
                    emit(emptyList())
                }
            })
    }

}