package com.example.database.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

enum class Guardian {
    SUN, MOON, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, PLUTO, NEPTUNE, NONE
}

class GuardianConverter {

    @TypeConverter
    fun fromGuardianList(list: List<Guardian>) = list.joinToString(",")

    @TypeConverter
    fun toGuardianList(value: String): List<Guardian> {
        val dbValues = value.split(",")
        return dbValues.map {
            Guardian.valueOf(it)
        }
    }
}