package com.daniel.helloworld.pertemuan12.database.repo

import com.daniel.helloworld.pertemuan12.database.Friend
import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    suspend fun searchFriend(keyword: String?): Flow<List<Friend>?>

    suspend fun getItemById(id: Int): Flow<Friend?>

    suspend fun getItemByString(value: String): Flow<List<Friend>?>
}