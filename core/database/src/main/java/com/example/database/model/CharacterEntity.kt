package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
@TypeConverters(DropOddsConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val id: String,
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
    fun jsonToMap(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun mapToJson(map: Map<String, Double>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}