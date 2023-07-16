package com.example.fusion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Fusion
import com.example.domain.repositories.FusionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFusionViewModel(
    private val fusionRepository: FusionRepository
) : ViewModel() {

    private val _allFusions: MutableLiveData<List<Fusion>> = MutableLiveData()
    val allFusions: LiveData<List<Fusion>> = _allFusions

    fun loadAllFusions() {
        viewModelScope.launch(Dispatchers.IO) {
            _allFusions.postValue(fusionRepository.getAllFusions())
        }
    }
}