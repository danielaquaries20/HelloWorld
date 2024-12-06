package com.daniel.helloworld.mytest.mahasiswa.data.repo.product

import com.crocodic.core.api.ApiObserver
import com.daniel.helloworld.mytest.mahasiswa.api.ApiService
import com.daniel.helloworld.mytest.mahasiswa.data.response.ProductResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ProductRepository {
    override fun getProducts(keyword: String) = flow {
//        val keywrd = keyword.ifEmpty { "" }
        ApiObserver.run(
            { apiService.searchProduct(keyword) },
            false,
            object : ApiObserver.ModelResponseListener<ProductResponse> {
                override suspend fun onSuccess(response: ProductResponse) {
                    emit(response.product)
                }

                override suspend fun onError(response: ProductResponse) {
                    emit(emptyList())
                }
            })
    }

}