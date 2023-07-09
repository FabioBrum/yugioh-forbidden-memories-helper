package com.example.database.mappers

import android.graphics.Bitmap
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

fun List<DomainCard>.toEntity() = this.map { it.toEntity() }

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

fun Pair<Guardian ,Guardian>.toEntity(): List<EntityGuardian> {

    return listOf(mapGuardianToEntity(this.first), mapGuardianToEntity(this.second))
}

private fun mapGuardianToEntity(guardian: Guardian) =
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

fun CardEntity.toDomain(image: Bitmap?) = DomainCard(
    id = id,
    name = name,
    type = type.toDomain(),
    nature = nature.toDomain(),
    guardians = guardians.toDomain(),
    level = level,
    attack = attack,
    defense = defense,
    password = password,
    starCost = starCost,
    image = image
)

fun EntityType.toDomain() =
    when(this) {
        EntityType.EQUIP -> Type.EQUIP
        EntityType.MAGIC -> Type.MAGIC
        EntityType.MONSTER -> Type.MONSTER
        EntityType.RITUAL -> Type.RITUAL
        EntityType.TRAP -> Type.TRAP
    }

fun EntityNature.toDomain() =
    when(this) {
        EntityNature.AQUA -> Nature.AQUA
        EntityNature.BEAST -> Nature.BEAST
        EntityNature.BEAST_WARRIOR -> Nature.BEAST_WARRIOR
        EntityNature.DINOSAUR -> Nature.DINOSAUR
        EntityNature.DRAGON -> Nature.DRAGON
        EntityNature.FAIRY -> Nature.FAIRY
        EntityNature.FIEND -> Nature.FIEND
        EntityNature.FISH -> Nature.FISH
        EntityNature.INSECT -> Nature.INSECT
        EntityNature.MACHINE -> Nature.MACHINE
        EntityNature.PLANT -> Nature.PLANT
        EntityNature.PYRO -> Nature.PYRO
        EntityNature.REPTILE -> Nature.REPTILE
        EntityNature.ROCK -> Nature.ROCK
        EntityNature.SEA_SERPENT -> Nature.SEA_SERPENT
        EntityNature.SPELL_CASTER -> Nature.SPELL_CASTER
        EntityNature.THUNDER -> Nature.THUNDER
        EntityNature.WARRIOR -> Nature.WARRIOR
        EntityNature.WINGED_BEAST -> Nature.WINGED_BEAST
        EntityNature.ZOMBIE -> Nature.ZOMBIE
        else -> Nature.NONE
    }

fun List<EntityGuardian>.toDomain(): Pair<Guardian, Guardian> {

    return Pair(mapGuardianToDomain(this.component1()), mapGuardianToDomain(this.component2()))
}

private fun mapGuardianToDomain(guardian: EntityGuardian) =
    when(guardian) {
        EntityGuardian.SUN -> Guardian.SUN
        EntityGuardian.MOON -> Guardian.MOON
        EntityGuardian.MERCURY -> Guardian.MERCURY
        EntityGuardian.VENUS -> Guardian.VENUS
        EntityGuardian.MARS -> Guardian.MARS
        EntityGuardian.JUPITER -> Guardian.JUPITER
        EntityGuardian.SATURN -> Guardian.SATURN
        EntityGuardian.URANUS -> Guardian.URANUS
        EntityGuardian.PLUTO -> Guardian.PLUTO
        EntityGuardian.NEPTUNE -> Guardian.NEPTUNE
        else -> Guardian.NONE
    }