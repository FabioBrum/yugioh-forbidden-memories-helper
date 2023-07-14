package com.example.database.mappers

import android.graphics.Bitmap
import com.example.database.model.CharacterEntity
import com.example.domain.model.Character as DomainCharacter

fun DomainCharacter.toEntity() = CharacterEntity(
    name = name,
    powSADropOdds = powSADropOdds,
    tecSADropOdds = tecSADropOdds,
    bcdDropOdds = bcdDropOdds
)

fun List<DomainCharacter>.toEntity() = this.map {
    it.toEntity()
}

fun CharacterEntity.toDomain(image: Bitmap?) = DomainCharacter(
    name = name,
    powSADropOdds = powSADropOdds,
    tecSADropOdds = tecSADropOdds,
    bcdDropOdds = bcdDropOdds,
    image = image
)