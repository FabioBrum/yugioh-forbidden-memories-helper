package com.example.domain.repositories

import com.example.domain.model.Fusion

interface FusionRepository {

    suspend fun getAllFusions(): List<Fusion>

    suspend fun saveFusion(fusion: Fusion)
}