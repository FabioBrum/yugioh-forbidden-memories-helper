package com.example.fusion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Card
import com.example.domain.model.Fusion
import com.example.domain.repositories.FusionRepository
import com.example.domain.repositories.OfflineCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateFusionViewModel(
    private val fusionRepository: FusionRepository,
    private val offlineCardRepository: OfflineCardRepository
) : ViewModel() {

    private val _allCards: MutableLiveData<List<Card>> = MutableLiveData()
    val allCards: LiveData<List<Card>> = _allCards

    private val _selectedCardOne: MutableLiveData<Card?> = MutableLiveData(null)
    val selectedCardOne: LiveData<Card?> = _selectedCardOne

    private val _selectedCardTwo: MutableLiveData<Card?> = MutableLiveData(null)
    val selectedCardTwo: LiveData<Card?> = _selectedCardTwo

    private val _selectedCardResult: MutableLiveData<Card?> = MutableLiveData(null)
    val selectedCardResult: LiveData<Card?> = _selectedCardResult

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allCards.postValue(
                offlineCardRepository.getAllCardsWithImages()
            )
        }
    }

    fun updateSelectedCards(card: Card) {
        if(selectedCardOne.value == null) {
            _selectedCardOne.value = card
            return
        }
        if(_selectedCardTwo.value == null) {
            _selectedCardTwo.value = card
            return
        }
        if(selectedCardResult.value == null) {
            _selectedCardResult.value = card
            return
        }
    }

    fun clearCardOne() {
        _selectedCardOne.value = null
    }

    fun clearCardTwo() {
        _selectedCardTwo.value = null
    }

    fun clearCardResult() {
        _selectedCardResult.value = null
    }

    fun saveFusion() = liveData(Dispatchers.IO) {
        val cardOne = selectedCardOne.value
        val cardTwo = selectedCardTwo.value
        val finalCard = selectedCardResult.value
        if(cardOne != null && cardTwo != null && finalCard != null) {
            emit(fusionRepository.saveFusion(Fusion(cardOne, cardTwo, finalCard)))
        }
    }
}