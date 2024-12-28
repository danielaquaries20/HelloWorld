package com.daniel.helloworld.mytest.mahasiswa.data.repo.product

import com.crocodic.core.api.ApiObserver
import com.daniel.helloworld.mytest.mahasiswa.api.ApiService
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.daniel.helloworld.mytest.mahasiswa.data.response.ProductResponse
import kotlinx.coroutines.flow.Flow
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

    override fun sortProducts(sortBy: String, orderBy: String): Flow<List<Product>> = flow {
        ApiObserver.run(
            { apiService.sortProduct(sortBy, orderBy) },
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


    override fun filterProducts(filterBy: String): Flow<List<Product>> = flow {
        ApiObserver.run(
            { apiService.filterProduct(filterBy) },
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

    override fun pagingProducts(limit: Int, skip: Int, select: String): Flow<List<Product>> {
        return flow {
            val response = apiService.pagingProducts(limit, skip, select)
            emit(response.product ?: return@flow)
        }
    }

    override fun sliderProducts(): Flow<List<Product>> = flow {
        ApiObserver.run(
            { apiService.slider() },
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