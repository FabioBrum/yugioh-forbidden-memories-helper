package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.database.DatabaseConstants

@Entity(
    primaryKeys = [
        DatabaseConstants.Fusion.card_one,
        DatabaseConstants.Fusion.card_two
    ],
    tableName = DatabaseConstants.Fusion.table_name
)
data class FusionEntity(
    @ColumnInfo(name = DatabaseConstants.Fusion.card_one)
    val cardOneId: String,
    @ColumnInfo(name = DatabaseConstants.Fusion.card_two)
    val cardTwoId: String,
    @ColumnInfo(name = DatabaseConstants.Fusion.final_card)
    val finalCardId: String
)
