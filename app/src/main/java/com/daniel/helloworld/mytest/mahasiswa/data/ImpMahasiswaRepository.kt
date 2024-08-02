package com.daniel.helloworld.mytest.mahasiswa.data

import kotlinx.coroutines.flow.Flow

class ImpMahasiswaRepository(private val dao: MahasiswaDao) : MahasiswaRepository {
    override fun getAllItems(): Flow<List<Mahasiswa>> = dao.getAllItems()

    override fun getItem(id: Int): Flow<Mahasiswa> = dao.getItem(id)

    override suspend fun insertItem(mahasiswa: Mahasiswa) = dao.insert(mahasiswa)

    override suspend fun deleteItem(mahasiswa: Mahasiswa) = dao.delete(mahasiswa)

    override suspend fun updateItem(mahasiswa: Mahasiswa) = dao.update(mahasiswa)
}