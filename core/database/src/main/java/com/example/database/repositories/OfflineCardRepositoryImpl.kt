package com.example.database.repositories

import android.content.Context
import com.example.database.Database
import com.example.database.mappers.toEntity
import com.example.domain.model.Card
import com.example.domain.repositories.OfflineCardRepository

class OfflineCardRepositoryImpl(
    private val context: Context
): OfflineCardRepository {

    private fun database() = Database.getDatabase(context)

    override suspend fun databaseHasCards(): Boolean {
        val cards = database().cardDao().getAllCards()
        return cards.isNotEmpty()
    }

    override suspend fun saveCards(cards: List<Card>) =
        database().cardDao().saveCards(cards.toEntity()).isNotEmpty()

}
