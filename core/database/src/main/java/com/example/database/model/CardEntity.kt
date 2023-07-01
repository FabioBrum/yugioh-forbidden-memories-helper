package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: Type,
    val nature: Nature,
    val guardians: List<Guardian>,
    val level: Int,
    val attack: Int,
    val defense: Int,
    val password: String,
    @ColumnInfo(name = "star_cost")
    val starCost: Int
)
