package com.example.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.DatabaseConstants
import com.example.database.model.FusionEntity

@Dao
interface FusionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFusion(fusion: FusionEntity)

    @Query("SELECT * FROM ${DatabaseConstants.Fusion.table_name}")
    fun getAllFusions(): List<FusionEntity>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Fusion.table_name} " +
        "WHERE ${DatabaseConstants.Fusion.card_one} = :cardId " +
        "OR ${DatabaseConstants.Fusion.card_two} = :cardId"
    )
    fun getAllFusionsGeneratedByCard(cardId: String): List<FusionEntity>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Fusion.table_name} " +
                "WHERE ${DatabaseConstants.Fusion.final_card} = :cardId"
    )
    fun getAllFusionsThatGenerateCard(cardId: String): List<FusionEntity>
}