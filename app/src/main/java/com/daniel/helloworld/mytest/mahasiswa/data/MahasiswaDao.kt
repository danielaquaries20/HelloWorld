package com.daniel.helloworld.mytest.mahasiswa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mahasiswa: Mahasiswa)

    @Update
    suspend fun update(mahasiswa: Mahasiswa)

    @Delete
    suspend fun delete(mahasiswa: Mahasiswa)

    @Query("SELECT * from mahasiswa WHERE id = :id")
    fun getItem(id: Int): Flow<Mahasiswa>

    @Query("SELECT * from mahasiswa ORDER BY nama ASC")
    fun getAllItems(): Flow<List<Mahasiswa>>
}