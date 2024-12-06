package com.daniel.helloworld.mytest.mahasiswa.data.repo.product

import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(keyword: String): Flow<List<Product>>
}