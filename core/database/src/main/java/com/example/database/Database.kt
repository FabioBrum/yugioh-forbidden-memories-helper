package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.model.CardEntity

@Database(entities = [CardEntity::class], version = 1)
abstract class Database: RoomDatabase() {
}