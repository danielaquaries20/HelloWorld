package com.daniel.helloworld.mytest.mahasiswa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0,
    var nim : String,
    var nama : String,
    var tglLahir : String,
    var alamat : String,
    var telepon: String,
    var photo: String
)
