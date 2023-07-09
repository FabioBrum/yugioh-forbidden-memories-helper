package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Card
import com.example.domain.repositories.OfflineCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainCardsViewModel(
    private val offlineCardRepository: OfflineCardRepository
) : ViewModel() {

    private val _allCards: MutableLiveData<List<Card>> = MutableLiveData()
    val allCards: LiveData<List<Card>> = _allCards

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allCards.postValue(offlineCardRepository.getAllCardsWithImages())
        }
    }
}