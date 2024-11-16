package com.daniel.helloworld.mytest.mahasiswa.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [Mahasiswa::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(2, 3),
        AutoMigration(3, 4, AppDatabase.RenameMhsBioToDescription::class),
        AutoMigration(4, 5, AppDatabase.DeleteMhsDescription::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    @RenameColumn("mahasiswa", "bio", "description")
    class RenameMhsBioToDescription : AutoMigrationSpec

    @DeleteColumn("mahasiswa", "description")
    class DeleteMhsDescription : AutoMigrationSpec

    abstract fun mahasiswaDao(): MahasiswaDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}