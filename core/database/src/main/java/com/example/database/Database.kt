package com.example.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.daos.CardDao
import com.example.database.daos.CharacterDao
import com.example.database.model.CardEntity
import com.example.database.model.CharacterEntity
import com.example.database.model.GuardianConverter
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [CardEntity::class, CharacterEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = com.example.database.Database.AutoMigrationSpec1to2::class)
    ],
)
abstract class Database: RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun characterDao(): CharacterDao

    companion object {

        @Volatile
        private var INSTANCE: com.example.database.Database? = null
        fun getDatabase(context: Context): com.example.database.Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.database.Database::class.java,
                    "canonical_database"
                )
                .createFromAsset("database.db")
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    @DeleteColumn(tableName = DatabaseConstants.Character.table_name, columnName = "id")
    class AutoMigrationSpec1to2 : AutoMigrationSpec {
        @Override
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            // Invoked once auto migration is done
        }
    }
}