package com.daniel.helloworld.pertemuan12.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Insert
    suspend fun insert(friend: Friend)

    @Update
    suspend fun editFriend(friend: Friend)

    @Query("SELECT * FROM friend")
    fun getAll(): Flow<List<Friend>>


    @Query("SELECT * from friend WHERE id = :id")
    fun getItemById(id: Int): Flow<Friend?>

    @Delete
    suspend fun delete(friend: Friend)

    @Query("SELECT * from friend WHERE name = :value")
    fun getItemByString(value: String): Flow<List<Friend>?>
}