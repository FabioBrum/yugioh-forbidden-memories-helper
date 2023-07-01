package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: Type,
    val nature: Nature,
    val guardians: Pair<Guardian, Guardian>,
    val level: Int,
    val attack: Int,
    val defense: Int,
    val password: String,
    val starCost: Int
)
