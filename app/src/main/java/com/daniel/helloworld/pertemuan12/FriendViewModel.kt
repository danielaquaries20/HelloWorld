package com.daniel.helloworld.pertemuan12

import androidx.lifecycle.ViewModel
import com.daniel.helloworld.pertemuan12.database.Friend
import com.daniel.helloworld.pertemuan12.database.FriendDao

class FriendViewModel(private val friendDao: FriendDao) : ViewModel() {

    fun getFriend() = friendDao.getAll()

    suspend fun insertFriend(data: Friend) {
        friendDao.insert(data)
    }
}