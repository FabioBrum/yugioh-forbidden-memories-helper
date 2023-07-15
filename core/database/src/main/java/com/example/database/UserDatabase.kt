package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.daos.FusionDao
import com.example.database.model.FusionEntity

@Database(
    entities = [FusionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun fusionDao(): FusionDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.database.UserDatabase::class.java,
                    "user_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}