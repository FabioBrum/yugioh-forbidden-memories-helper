package com.example.domain.repositories

import com.example.domain.model.Card

interface OfflineCardRepository {

    suspend fun databaseHasCards(): Boolean

    suspend fun saveCards(cards: List<Card>): Boolean
}