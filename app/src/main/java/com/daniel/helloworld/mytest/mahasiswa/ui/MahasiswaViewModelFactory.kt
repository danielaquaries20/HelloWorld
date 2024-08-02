package com.daniel.helloworld.mytest.mahasiswa.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daniel.helloworld.mytest.mahasiswa.data.AppDatabase

class MahasiswaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MahasiswaViewModel(AppDatabase.getDatabase(context).mahasiswaDao()) as T
    }
}