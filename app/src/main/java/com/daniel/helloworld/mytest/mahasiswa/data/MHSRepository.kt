package com.daniel.helloworld.mytest.mahasiswa.data

import kotlinx.coroutines.flow.Flow

interface MHSRepository {

    fun getAllItems(): Flow<List<Mahasiswa>?>

    fun getItem(id: Int): Flow<Mahasiswa?>

    suspend fun insertItem(mahasiswa: Mahasiswa)

    suspend fun deleteItem(mahasiswa: Mahasiswa)

    suspend fun updateItem(mahasiswa: Mahasiswa)
}