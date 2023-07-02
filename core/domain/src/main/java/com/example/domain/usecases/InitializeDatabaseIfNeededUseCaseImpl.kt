package com.example.domain.usecases

import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.repositories.OnlineCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.lang.Exception

class InitializeDatabaseIfNeededUseCaseImpl(
    private val offlineCardRepository: OfflineCardRepository,
    private val onlineCardsRepository: OnlineCardsRepository,
    private val onlineCharacterRepository: OnlineCharacterRepository
): InitializeDatabaseIfNeededUseCase {

    override suspend fun invoke(coroutineScope: CoroutineScope): Boolean  {
        return with(coroutineScope) {
            try {
                if (offlineCardRepository.databaseHasCards()) {
                    true
                } else {
                    val cardsAndCardLinks = onlineCardsRepository.downloadCardsWithCardLinks()
                    val cards = cardsAndCardLinks.first
                    val cardLinks = cardsAndCardLinks.second

                    cardLinks.mapIndexed { index, cardLink ->
                        with(this) {
                            async {
                                onlineCardsRepository.downloadCardImage(cards[index].id, cardLink)
                            }
                        }
                    }.awaitAll()

                    offlineCardRepository.saveCards(cards)
                }
                // onlineCharacterRepository.downloadCharacters()
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}