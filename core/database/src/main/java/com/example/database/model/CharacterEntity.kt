package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.DatabaseConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = DatabaseConstants.Character.table_name)
@TypeConverters(DropOddsConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "pow_sa_drop_odds")
    val powSADropOdds: Map<String, Double>,
    @ColumnInfo(name = "tec_sa_drop_odds")
    val tecSADropOdds: Map<String, Double>,
    @ColumnInfo(name = "bcd_drop_odds")
    val bcdDropOdds: Map<String, Double>
)

class DropOddsConverter {

    @TypeConverter
    fun stringToMap(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun mapToString(map: Map<String, Double>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}