package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ListType
import com.example.domain.model.OrderBy
import com.example.domain.model.Ordination
import com.example.domain.model.CardListFilters
import com.example.domain.model.Card
import com.example.domain.model.allNatures
import com.example.domain.model.allTypes
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.usecases.FilterCardsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainCardsViewModel(
    private val filterCardsUseCase: FilterCardsUseCase,
    private val offlineCardRepository: OfflineCardRepository
) : ViewModel() {

    private val _allCards: MutableLiveData<List<Card>> = MutableLiveData()
    val allCards: LiveData<List<Card>> = _allCards

    private val _filters: MutableLiveData<CardListFilters> = MutableLiveData(
        CardListFilters(
            listType = ListType.EXPENDED,
            orderBy = OrderBy.ATTACK,
            attackRange = Pair(0, 4500),
            defenseRange = Pair(0, 4500),
            ordination = Ordination.DESCENDING,
            cardTypes = allTypes,
            cardNatures = allNatures
        )
    )
    val filters: LiveData<CardListFilters> = _filters

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allCards.postValue(offlineCardRepository.getAllCardsWithImages())
        }
    }

    fun updateFilters(newFilters: CardListFilters?) {
        newFilters?.let {
            _filters.postValue(it)
        }
    }

    fun filterCards() : List<Card> {
        return filterCardsUseCase(allCards.value!!, filters.value!!)
    }
}