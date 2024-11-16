package com.daniel.helloworld.mytest.mahasiswa.data

import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao : CoreDao<Mahasiswa> {

    @Query("SELECT * from mahasiswa WHERE id = :id")
    fun getItem(id: Int): Flow<Mahasiswa?>

    @Query("SELECT * from mahasiswa ORDER BY nama ASC")
    fun getAllItems(): Flow<List<Mahasiswa>?>

    @Query("SELECT * FROM mahasiswa WHERE nama LIKE :keyword OR nim LIKE :keyword")
    fun findMhs(keyword: String): Flow<List<Mahasiswa>>

    @Query("SELECT * from mahasiswa")
    fun findMhs(): Flow<List<Mahasiswa>>

    fun searchFriend(keyword: String? = null): Flow<List<Mahasiswa>> {
        return if (keyword.isNullOrEmpty()) findMhs() else findMhs("%$keyword%")
    }
}