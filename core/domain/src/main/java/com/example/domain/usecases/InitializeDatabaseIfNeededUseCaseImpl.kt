package com.example.domain.usecases

import android.util.Log
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OfflineCharacterRepository
import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.repositories.OnlineCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.lang.Exception

class InitializeDatabaseIfNeededUseCaseImpl(
    private val offlineCardRepository: OfflineCardRepository,
    private val onlineCardsRepository: OnlineCardsRepository,
    private val onlineCharacterRepository: OnlineCharacterRepository,
    private val offlineCharacterRepository: OfflineCharacterRepository
): InitializeDatabaseIfNeededUseCase {

    override suspend fun invoke(coroutineScope: CoroutineScope): Boolean  {
        return with(coroutineScope) {
            try {
                if (!offlineCardRepository.databaseHasCards()) {
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

                    val characterPageLinks = onlineCharacterRepository.getCharacterPageLinks()
                    val characters = characterPageLinks.map { onlineCharacterRepository.downloadCharacter(it) }

                    offlineCardRepository.saveCards(cards) &&
                    offlineCharacterRepository.saveCharacters(characters.filterNotNull())
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
