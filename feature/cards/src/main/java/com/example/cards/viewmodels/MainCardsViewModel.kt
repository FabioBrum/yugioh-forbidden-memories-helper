package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cards.model.ListType
import com.example.cards.model.OrderBy
import com.example.cards.model.UICardListFilters
import com.example.domain.model.Card
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
            orderBy = OrderBy.NAME,
            attackRange = Pair(0, 10000),
            defenseRange = Pair(0, 10000)
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
        return allCards.value?.let { listOfCards ->
            listOfCards.filter { card ->
                card.attack in (filters.attackRange.first .. filters.attackRange.second) &&
                card.defense in (filters.defenseRange.first .. filters.defenseRange.second)
            }.sortedBy {
                when(filters.orderBy) {
                    OrderBy.NAME -> it.name
                    OrderBy.ATTACK -> it.attack.toString()
                    OrderBy.DEFENSE -> it.defense.toString()
                }
            }
        } ?: emptyList()
    }
}