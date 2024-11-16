package com.daniel.helloworld.mytest.mahasiswa.ui

import androidx.lifecycle.viewModelScope
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.MahasiswaDao
import com.daniel.helloworld.mytest.mahasiswa.data.repo.MahasiswaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MahasiswaViewModel @Inject constructor(
    private val mhsDao: MahasiswaDao,
    private val mahasiswaRepository: MahasiswaRepository
) : CoreViewModel() {


    private val _mhs = MutableSharedFlow<List<Mahasiswa>>()
    val mhs = _mhs.asSharedFlow()

    fun getMhs(keyword: String) = viewModelScope.launch {
        mahasiswaRepository.searchMhs(keyword).collect {
            _mhs.emit(it)
        }
    }

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

    override fun apiLogout() {

    }

    override fun apiRenewToken() {
    }

}