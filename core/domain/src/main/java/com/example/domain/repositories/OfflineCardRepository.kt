package com.example.domain.repositories

import com.example.domain.model.Card

interface OfflineCardRepository {

    suspend fun databaseHasCards(): Boolean

    suspend fun saveCards(cards: List<Card>): Boolean

    suspend fun getAllCardsWithImages(): List<Card>

    suspend fun getCard(cardId: String): Card?

    suspend fun getCards(cardIds: List<String>): List<Card>
}