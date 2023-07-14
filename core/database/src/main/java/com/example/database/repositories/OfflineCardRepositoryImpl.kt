package com.example.database.repositories

import android.content.Context
import android.graphics.BitmapFactory
import com.example.database.Database
import com.example.database.mappers.toDomain
import com.example.database.mappers.toEntity
import com.example.domain.model.Card
import com.example.domain.repositories.OfflineCardRepository
import java.io.IOException

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

    override suspend fun getAllCardsWithImages(): List<Card> {
        val cards = database().cardDao().getAllCards()
        return cards.map { card ->
            try {
                val cardImage = context.assets.open("cardImages/${card.id}.jpeg")
                card.toDomain(
                    BitmapFactory.decodeStream(cardImage)
                )
            } catch (e: IOException) {
                card.toDomain(null)
            }
        }
    }

    override suspend fun getCard(cardId: String): Card? {
        val card = database().cardDao().getCard(cardId)
        return try {
            val cardImage = context.assets.open("cardImages/${card?.id}.jpeg")
            card?.toDomain(
                BitmapFactory.decodeStream(cardImage)
            )
        } catch (e: IOException) {
            card?.toDomain(null)
        }
    }

    override suspend fun getCards(cardIds: List<String>): List<Card> {
        return database().cardDao().getCards(cardIds).map { it.toDomain(null) }
    }

}
