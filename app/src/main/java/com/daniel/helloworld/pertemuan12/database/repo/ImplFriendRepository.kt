package com.daniel.helloworld.pertemuan12.database.repo

import com.daniel.helloworld.pertemuan12.database.Friend
import com.daniel.helloworld.pertemuan12.database.FriendDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImplFriendRepository @Inject constructor(private val friendDao: FriendDao) :
    FriendRepository {

    override suspend fun searchFriend(keyword: String?): Flow<List<Friend>?> {
        return friendDao.searchFriend(keyword)
    }

    override suspend fun getItemById(id: Int): Flow<Friend?> {
        return friendDao.getItemById(id)
    }

    override suspend fun getItemByString(value: String): Flow<List<Friend>?> {
        return friendDao.getItemByString(value)
    }
}