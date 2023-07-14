package com.example.characters.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.characters.model.OddType
import com.example.characters.model.UICardWithOdd
import com.example.domain.model.Card
import com.example.domain.model.Character
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OfflineCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val offlineCardRepository: OfflineCardRepository,
    private val offlineCharacterRepository: OfflineCharacterRepository
) : ViewModel() {

    private val _character: MutableLiveData<Character?> = MutableLiveData()
    val character: LiveData<Character?> = _character

    private val _uICardsWithOdds: MutableLiveData<Map<OddType,List<UICardWithOdd>>> = MutableLiveData()
    val uICardsWithOdds: LiveData<Map<OddType,List<UICardWithOdd>>> = _uICardsWithOdds

    fun getCharacterInfo(characterName: String) = viewModelScope.launch(Dispatchers.IO) {
        val character = offlineCharacterRepository.getCharacterWithImage(characterName)
        _character.postValue(character)
        character?.let {
            loadCardsWithOdds(listOfCardIds =
                (it.powSADropOdds.keys +
                it.tecSADropOdds.keys +
                it.bcdDropOdds.keys).distinct(),
                character
            )
        }
    }

    private suspend fun loadCardsWithOdds(listOfCardIds: List<String>, character: Character) {
        val cards = offlineCardRepository.getCards(listOfCardIds)

        val uiCardWithPOWSAOdd = mapCardsToUICardsWithOdds(cards, character.powSADropOdds)

        val uiCardWithTECSAOdd = mapCardsToUICardsWithOdds(cards, character.tecSADropOdds)

        val uiCardWithBCDOdd = mapCardsToUICardsWithOdds(cards, character.bcdDropOdds)

        _uICardsWithOdds.postValue(mapOf(
            OddType.POW_SA to uiCardWithPOWSAOdd,
            OddType.TEC_SA to uiCardWithTECSAOdd,
            OddType.POW_TEC_BCD to uiCardWithBCDOdd
        ))
    }

    private fun mapCardsToUICardsWithOdds(cards: List<Card>, oddsList: Map<String, Double>) =
        cards.mapNotNull {
            oddsList[it.id]?.let { cardOdds ->
                UICardWithOdd(
                    cardId = it.id,
                    cardName = it.name,
                    cardAttributes = "${it.attack}/${it.defense}",
                    cardOdds = cardOdds
                )
            }
        }.sortedBy { it.cardOdds }.reversed()

    fun loadCard(cardId: String) = liveData(Dispatchers.IO) {
        emit(
            offlineCardRepository.getCard(cardId)
        )
    }
}