package com.example.domain.usecases

import android.util.Log
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OnlineCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

class InitializeDatabaseIfNeededUseCaseImpl(
    private val offlineCardRepository: OfflineCardRepository,
    private val onlineCardsRepository: OnlineCardsRepository,
): InitializeDatabaseIfNeededUseCase {

    override suspend fun invoke(): Flow<Boolean> = callbackFlow {
        try {
            if (offlineCardRepository.databaseHasCards()) {
                trySend(true)
            } else {
                val cards = onlineCardsRepository.downloadCards()
                val result = offlineCardRepository.saveCards(cards)
                trySend(result)
            }
        } catch(e: Exception) {
            Log.i("Erro teste", e.toString())
            trySend(false)
        } finally {
            this.channel.close()
        }
    }
}