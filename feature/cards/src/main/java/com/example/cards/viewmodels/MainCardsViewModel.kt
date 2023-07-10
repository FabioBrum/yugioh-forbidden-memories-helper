package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cards.model.ListType
import com.example.cards.model.OrderBy
import com.example.cards.model.Ordination
import com.example.cards.model.UICardListFilters
import com.example.domain.model.Card
import com.example.domain.model.Type
import com.example.domain.repositories.OfflineCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainCardsViewModel(
    private val offlineCardRepository: OfflineCardRepository
) : ViewModel() {

    private val _allCards: MutableLiveData<List<Card>> = MutableLiveData()
    val allCards: LiveData<List<Card>> = _allCards

    private val _filters: MutableLiveData<UICardListFilters> = MutableLiveData(
        UICardListFilters(
            listType = ListType.EXPENDED,
            orderBy = OrderBy.ATTACK,
            attackRange = Pair(0, 4500),
            defenseRange = Pair(0, 4500),
            ordination = Ordination.DESCENDING,
            cardTypes = listOf(Type.TRAP, Type.EQUIP, Type.RITUAL, Type.MONSTER, Type.MAGIC)
        )
    )
    val filters: LiveData<UICardListFilters> = _filters

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allCards.postValue(offlineCardRepository.getAllCardsWithImages())
        }
    }

    fun updateFilters(newFilters: UICardListFilters?) {
        newFilters?.let {
            _filters.postValue(it)
        }
    }

    fun filterCards() : List<Card> {
        val filters = filters.value!!
        var filteredCards = allCards.value?.let { listOfCards ->
            listOfCards.filter { card ->
                card.attack in (filters.attackRange.first .. filters.attackRange.second) &&
                card.defense in (filters.defenseRange.first .. filters.defenseRange.second) &&
                filters.cardTypes.contains(card.type)
            }
        } ?: emptyList()

        filteredCards = when(filters.orderBy) {
            OrderBy.NAME -> filteredCards.sortedBy { it.name }
            OrderBy.ATTACK -> filteredCards.sortedBy { it.attack }
            OrderBy.DEFENSE -> filteredCards.sortedBy { it.defense }
        }

        return when(filters.ordination) {
            Ordination.ASCENDING -> filteredCards
            Ordination.DESCENDING -> filteredCards.reversed()
        }
    }
}