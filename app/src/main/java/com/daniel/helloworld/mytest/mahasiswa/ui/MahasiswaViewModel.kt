package com.daniel.helloworld.mytest.mahasiswa.ui

import androidx.lifecycle.ViewModel
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.MahasiswaDao

class MahasiswaViewModel(private val mhsDao: MahasiswaDao) : ViewModel() {

    fun getMhs() = mhsDao.getAllItems()

    fun getMhsById(id: Int) = mhsDao.getItem(id)

    suspend fun insertMhs(mhs: Mahasiswa) {
        mhsDao.insert(mhs)
    }

    suspend fun updateMhs(mhs: Mahasiswa) {
        mhsDao.update(mhs)
    }

    suspend fun deleteMhs(mhs: Mahasiswa) {
        mhsDao.delete(mhs)
    }

}