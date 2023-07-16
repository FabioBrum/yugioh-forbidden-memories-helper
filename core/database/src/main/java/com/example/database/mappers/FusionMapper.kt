package com.example.database.mappers

import com.example.database.model.FusionEntity
import com.example.domain.model.Fusion as FusionDomain

fun FusionDomain.toEntity() = FusionEntity(
    cardOneId = cardOne.id,
    cardTwoId = cardTwo.id,
    finalCardId = finalCard.id
)