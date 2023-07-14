package com.example.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.DatabaseConstants
import com.example.database.model.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCharacters(cards: List<CharacterEntity>): List<Long>

    @Query("SELECT * FROM ${DatabaseConstants.Character.table_name}")
    fun getAllCharacters(): List<CharacterEntity>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Character.table_name} " +
                "WHERE ${DatabaseConstants.Character.name} = :characterName"
    )
    fun getCharactersByName(characterName: String): CharacterEntity?
}