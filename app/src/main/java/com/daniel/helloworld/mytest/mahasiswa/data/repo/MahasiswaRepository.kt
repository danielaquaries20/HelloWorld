package com.daniel.helloworld.mytest.mahasiswa.data.repo

import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository {

    suspend fun searchMhs(keyword: String) : Flow<List<Mahasiswa>>

}