package com.daniel.helloworld.mytest.mahasiswa.ui

import androidx.lifecycle.ViewModel
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.MahasiswaDao

class MahasiswaViewModel(private val mhsDao: MahasiswaDao) : ViewModel() {

    fun getMhs() = mhsDao.getAllItems()

    suspend fun insertMhs(mhs: Mahasiswa) {
        mhsDao.insert(mhs)
    }
}