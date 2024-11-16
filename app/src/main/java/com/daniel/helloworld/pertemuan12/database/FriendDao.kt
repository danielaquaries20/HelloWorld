package com.daniel.helloworld.pertemuan12.database

import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao : CoreDao<Friend> {

    @Query("SELECT * FROM friend")
    fun getAll(): Flow<List<Friend>>

    @Query("SELECT * from friend WHERE id = :id")
    fun getItemById(id: Int): Flow<Friend?>


    @Query("SELECT * from friend WHERE name = :value")
    fun getItemByString(value: String): Flow<List<Friend>?>

    @Query("SELECT * FROM friend WHERE name LIKE :keyword OR school LIKE :keyword")
     fun findFriend(keyword: String): Flow<List<Friend>?>

    @Query("SELECT * FROM friend")
     fun findFriend(): Flow<List<Friend>?>

     fun searchFriend(keyword: String? = null): Flow<List<Friend>?> {
        return if (keyword.isNullOrEmpty()) {
            findFriend()
        } else {
            findFriend(keyword)
        }
    }
}