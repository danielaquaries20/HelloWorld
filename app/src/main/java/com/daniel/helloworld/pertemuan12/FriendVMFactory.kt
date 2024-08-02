package com.daniel.helloworld.pertemuan12

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daniel.helloworld.pertemuan12.database.MyDatabase

class FriendVMFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FriendViewModel(MyDatabase.getInstance(context).friendDao()) as T
    }
}