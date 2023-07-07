package com.example.database.mappers

import com.example.database.model.CharacterEntity
import com.example.domain.model.Character as DomainCharacter

fun DomainCharacter.toEntity() = CharacterEntity(
    id = id,
    name = name,
    powSADropOdds = powSADropOdds,
    tecSADropOdds = tecSADropOdds,
    bcdDropOdds = bcdDropOdds
)

fun List<DomainCharacter>.toEntity() = this.map {
    it.toEntity()
}