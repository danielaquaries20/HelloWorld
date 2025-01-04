package com.daniel.helloworld.pertemuan12.repository

import android.util.Log
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
                    Log.d("API", "Data Response: ${response.products}")
                    emit(response.products)
                }

                override suspend fun onError(response: ResponseDataProduct) {
                    emit(emptyList())
                }
            })
    }

    override fun sortProducts(sortBy: String, order: String): Flow<List<DataProduct>> = flow {
        ApiObserver.run(
            { apiServiceProduct.sortProducts(sortBy, order) },
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

    override fun filterProducts(filter: String): Flow<List<DataProduct>> = flow {
        ApiObserver.run(
            { apiServiceProduct.filterProducts(filter) },
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

    override fun pagingProducts(limit: Int, skip: Int): Flow<List<DataProduct>> {
        return flow {
            val response = apiServiceProduct.pagingProduct(limit, skip)
            emit(response.products ?: return@flow)
        }
    }

    override fun getSlider(): Flow<List<DataProduct>> = flow {
        ApiObserver.run(
            { apiServiceProduct.getSlider() },
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