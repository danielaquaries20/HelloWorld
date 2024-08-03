package com.daniel.helloworld.pertemuan12.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend")
data class Friend(
    var name : String,
    var school : String,
    var hobby : String,
    var photo : String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
