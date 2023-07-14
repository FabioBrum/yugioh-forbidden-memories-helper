package com.example.characters.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characters.model.UICharacter
import com.example.characters.model.toUI
import com.example.domain.repositories.OfflineCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainCharactersViewModel(
    private val offlineCharacterRepository: OfflineCharacterRepository
) : ViewModel() {

    private val _allCharacters: MutableLiveData<List<UICharacter>> = MutableLiveData()
    val allCharacters: LiveData<List<UICharacter>> = _allCharacters

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allCharacters = offlineCharacterRepository.getAllCharactersWithImages()
            _allCharacters.postValue(allCharacters.toUI().sortedBy { it.name })
        }
    }
}