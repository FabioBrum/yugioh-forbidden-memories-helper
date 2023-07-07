package com.example.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.DatabaseConstants
import com.example.database.model.CardEntity
import com.example.database.model.CharacterEntity
import com.example.domain.model.Card

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCharacters(cards: List<CharacterEntity>): List<Long>
}