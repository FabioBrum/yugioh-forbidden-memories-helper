package com.example.domain.usecases

import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OnlineCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

class InitializeDatabaseIfNeededUseCaseImpl(
    private val onlineCardsRepository: OnlineCardsRepository,
): InitializeDatabaseIfNeededUseCase {

    override suspend fun invoke(): Flow<Boolean> = callbackFlow {
        try {
            if (false) {
                trySend(true)
            } else {
                val cards = onlineCardsRepository.downloadCards()
                // save Cards in database
                trySend(true)
            }
        } catch(_: Exception) {
            trySend(false)
        } finally {
            this.channel.close()
        }
    }
}