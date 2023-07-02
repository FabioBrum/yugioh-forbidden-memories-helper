package com.example.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.daos.CardDao
import com.example.database.model.CardEntity
import com.example.database.model.GuardianConverter
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [CardEntity::class],
    version = 1,
)
abstract class Database: RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        @Volatile
        private var INSTANCE: com.example.database.Database? = null
        fun getDatabase(context: Context): com.example.database.Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.database.Database::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}