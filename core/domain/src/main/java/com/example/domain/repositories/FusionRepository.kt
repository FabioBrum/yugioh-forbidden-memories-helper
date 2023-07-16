package com.example.domain.repositories

import com.example.domain.model.Fusion

interface FusionRepository {

    suspend fun getAllFusionsGeneratedByCard(cardId: String): List<Fusion>

    suspend fun getAllFusionsThatGenerateCard(cardId: String): List<Fusion>

    suspend fun getAllFusions(): List<Fusion>

    suspend fun saveFusion(fusion: Fusion)
}