package com.daniel.helloworld.pertemuan12.repository

import com.daniel.helloworld.pertemuan12.data.DataProduct
import kotlinx.coroutines.flow.Flow

interface DataProductsRepo {

    fun getProducts(keyword: String): Flow<List<DataProduct>>

    fun sortProducts(sortBy: String, order: String): Flow<List<DataProduct>>

    fun filterProducts(filter: String): Flow<List<DataProduct>>

    fun pagingProducts(limit: Int, skip: Int): Flow<List<DataProduct>>

    fun getSlider() : Flow<List<DataProduct>>
}