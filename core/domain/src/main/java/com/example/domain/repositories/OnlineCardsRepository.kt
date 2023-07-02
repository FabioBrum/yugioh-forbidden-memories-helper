package com.example.domain.repositories

import com.example.domain.model.Card
import kotlinx.coroutines.CoroutineScope

interface OnlineCardsRepository {
    suspend fun downloadCardsWithCardLinks(): Pair<List<Card>, List<String>>

    suspend fun downloadCardImage(cardId: String, cardLink: String)
}