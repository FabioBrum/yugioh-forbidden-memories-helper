package com.example.domain.repositories

import com.example.domain.model.Card

interface OnlineCardsRepository {
    suspend fun downloadCards(): List<Card>
}