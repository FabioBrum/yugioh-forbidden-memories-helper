package com.example.fusion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Card
import com.example.domain.repositories.OfflineCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CreateFusionViewModel(
    private val offlineCardRepository: OfflineCardRepository
) : ViewModel() {

    private val _allCards: MutableLiveData<List<Card>> = MutableLiveData()
    val allCards: LiveData<List<Card>> = _allCards

    private val _selectedCardOneId: MutableLiveData<String?> = MutableLiveData(null)
    val selectedCardOneId: LiveData<String?> = _selectedCardOneId

    private val _selectedCardTwoId: MutableLiveData<String?> = MutableLiveData(null)
    val selectedCardTwoId: LiveData<String?> = _selectedCardTwoId

    private val _selectedCardResultId: MutableLiveData<String?> = MutableLiveData(null)
    val selectedCardResultId: LiveData<String?> = _selectedCardResultId

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allCards.postValue(
                offlineCardRepository.getAllCardsWithImages()
            )
        }
    }

    fun updateSelectedCards(id: String) {
        if(selectedCardOneId.value == null) {
            _selectedCardOneId.value = id
            return
        }
        if(_selectedCardTwoId.value == null) {
            _selectedCardTwoId.value = id
            return
        }
        if(selectedCardResultId.value == null) {
            _selectedCardResultId.value = id
            return
        }
    }

    fun clearCardOne() {
        _selectedCardOneId.value = null
    }

    fun clearCardTwo() {
        _selectedCardTwoId.value = null
    }

    fun clearCardResult() {
        _selectedCardResultId.value = null
    }
}