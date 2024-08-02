package com.daniel.helloworld.mytest.mahasiswa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val nim : String,
    val nama : String,
    val tglLahir : String,
    val alamat : String,
    val telepon: String
)
