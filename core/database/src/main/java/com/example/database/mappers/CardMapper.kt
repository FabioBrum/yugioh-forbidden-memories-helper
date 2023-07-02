package com.example.database.mappers

import com.example.database.model.CardEntity
import com.example.domain.model.Guardian
import com.example.database.model.Guardian as EntityGuardian
import com.example.domain.model.Nature
import com.example.database.model.Nature as EntityNature
import com.example.domain.model.Type
import com.example.database.model.Type as EntityType
import com.example.domain.model.Card as DomainCard

fun DomainCard.toEntity() = CardEntity(
    id = id,
    name = name,
    type = type.toEntity(),
    nature = nature.toEntity(),
    guardians = guardians.toEntity(),
    level = level,
    attack = attack,
    defense = defense,
    password = password,
    starCost = starCost
)

fun List<DomainCard>.toEntity() = this.map { CardEntity(
        id = it.id,
        name = it.name,
        type = it.type.toEntity(),
        nature = it.nature.toEntity(),
        guardians = it.guardians.toEntity(),
        level = it.level,
        attack = it.attack,
        defense = it.defense,
        password = it.password,
        starCost = it.starCost
    )
}

fun Type.toEntity() =
    when(this) {
        Type.EQUIP -> EntityType.EQUIP
        Type.MAGIC -> EntityType.MAGIC
        Type.MONSTER -> EntityType.MONSTER
        Type.RITUAL -> EntityType.RITUAL
        Type.TRAP -> EntityType.TRAP
        else -> EntityType.EQUIP
    }

fun Nature.toEntity() =
    when(this) {
        Nature.AQUA -> EntityNature.AQUA
        Nature.BEAST -> EntityNature.BEAST
        Nature.BEAST_WARRIOR -> EntityNature.BEAST_WARRIOR
        Nature.DINOSAUR -> EntityNature.DINOSAUR
        Nature.DRAGON -> EntityNature.DRAGON
        Nature.FAIRY -> EntityNature.FAIRY
        Nature.FIEND -> EntityNature.FIEND
        Nature.FISH -> EntityNature.FISH
        Nature.INSECT -> EntityNature.INSECT
        Nature.MACHINE -> EntityNature.MACHINE
        Nature.PLANT -> EntityNature.PLANT
        Nature.PYRO -> EntityNature.PYRO
        Nature.REPTILE -> EntityNature.REPTILE
        Nature.ROCK -> EntityNature.ROCK
        Nature.SEA_SERPENT -> EntityNature.SEA_SERPENT
        Nature.SPELL_CASTER -> EntityNature.SPELL_CASTER
        Nature.THUNDER -> EntityNature.THUNDER
        Nature.WARRIOR -> EntityNature.WARRIOR
        Nature.WINGED_BEAST -> EntityNature.WINGED_BEAST
        Nature.ZOMBIE -> EntityNature.ZOMBIE
        else -> EntityNature.NONE
    }

fun Pair<Guardian, Guardian>.toEntity(): List<EntityGuardian> {

    return listOf(mapGuardian(this.first), mapGuardian(this.second))
}

private fun mapGuardian(guardian: Guardian) =
    when(guardian) {
        Guardian.SUN -> EntityGuardian.SUN
        Guardian.MOON -> EntityGuardian.MOON
        Guardian.MERCURY -> EntityGuardian.MERCURY
        Guardian.VENUS -> EntityGuardian.VENUS
        Guardian.MARS -> EntityGuardian.MARS
        Guardian.JUPITER -> EntityGuardian.JUPITER
        Guardian.SATURN -> EntityGuardian.SATURN
        Guardian.URANUS -> EntityGuardian.URANUS
        Guardian.PLUTO -> EntityGuardian.PLUTO
        Guardian.NEPTUNE -> EntityGuardian.NEPTUNE
        else -> EntityGuardian.NONE
    }