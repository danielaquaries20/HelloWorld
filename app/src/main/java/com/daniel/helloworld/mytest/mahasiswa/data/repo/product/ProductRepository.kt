package com.daniel.helloworld.mytest.mahasiswa.data.repo.product

import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(keyword: String): Flow<List<Product>>

    fun sortProducts(sortBy: String, orderBy: String): Flow<List<Product>>

    fun filterProducts(filterBy: String): Flow<List<Product>>

    fun pagingProducts(limit: Int, skip: Int, select: String): Flow<List<Product>>
}