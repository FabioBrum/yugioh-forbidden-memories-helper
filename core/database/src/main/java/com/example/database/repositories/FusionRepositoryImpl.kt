package com.example.database.repositories

import android.content.Context
import com.example.database.UserDatabase
import com.example.database.mappers.toEntity
import com.example.domain.model.Fusion
import com.example.domain.repositories.FusionRepository
import com.example.domain.repositories.OfflineCardRepository

class FusionRepositoryImpl(
    private val context: Context,
    private val cardRepository: OfflineCardRepository
): FusionRepository {

    private val userDatabase = UserDatabase.getDatabase(context)

    override suspend fun getAllFusions() =
        userDatabase.fusionDao().getAllFusions().mapNotNull {
            val cardOne = cardRepository.getCard(it.cardOneId)
            val cardTwo = cardRepository.getCard(it.cardTwoId)
            val finalCard = cardRepository.getCard(it.finalCardId)
            if(cardOne != null && cardTwo != null && finalCard != null) {
                Fusion(cardOne, cardTwo, finalCard)
            } else {
                null
            }
        }

    override suspend fun saveFusion(fusion: Fusion) =
        userDatabase.fusionDao().saveFusion(fusion.toEntity())
}