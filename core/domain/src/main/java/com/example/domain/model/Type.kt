package com.example.domain.model

enum class Type {
    EQUIP, MAGIC, MONSTER, RITUAL, TRAP
}

val allTypes = listOf(Type.TRAP, Type.EQUIP, Type.RITUAL, Type.MONSTER, Type.MAGIC)