package com.example.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.DatabaseConstants
import com.example.database.model.CharacterEntity
import com.example.database.model.FusionEntity

@Dao
interface FusionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFusion(fusion: FusionEntity)

    @Query("SELECT * FROM ${DatabaseConstants.Fusion.table_name}")
    fun getAllFusions(): List<FusionEntity>

}