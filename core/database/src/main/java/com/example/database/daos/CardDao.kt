package com.example.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.DatabaseConstants
import com.example.database.model.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM ${DatabaseConstants.Card.table_name} ")
    fun getAllCards(): List<CardEntity>

    @Insert(entity = CardEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCards(cards: List<CardEntity>): List<Long>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Card.table_name} " +
                "WHERE ${DatabaseConstants.Card.id} = :cardId"
    )
    fun getCard(cardId: String): CardEntity?

    @Query(
        "SELECT * FROM ${DatabaseConstants.Card.table_name} " +
            "WHERE ${DatabaseConstants.Card.id} IN (:cardIds)"
    )
    fun getCards(cardIds: List<String>): List<CardEntity>
}