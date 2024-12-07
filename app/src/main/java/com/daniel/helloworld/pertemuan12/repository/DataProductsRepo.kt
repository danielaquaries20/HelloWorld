package com.daniel.helloworld.pertemuan12.repository

import com.daniel.helloworld.pertemuan12.data.DataProduct
import kotlinx.coroutines.flow.Flow

interface DataProductsRepo {

    fun getProducts(keyword: String): Flow<List<DataProduct>>
}