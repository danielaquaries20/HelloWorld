package com.daniel.helloworld.mytest.mahasiswa.data.repo

import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.MahasiswaDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MahasiswaRepositoryImpl @Inject constructor(private val mahasiswaDao: MahasiswaDao) : MahasiswaRepository {

    override suspend fun searchMhs(keyword: String): Flow<List<Mahasiswa>> {
        return mahasiswaDao.searchFriend(keyword)
    }
}