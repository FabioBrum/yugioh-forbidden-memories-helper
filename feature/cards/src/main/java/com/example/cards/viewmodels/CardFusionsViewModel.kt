package com.example.cards.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.domain.repositories.FusionRepository
import kotlinx.coroutines.Dispatchers

class CardFusionsViewModel(
    private val fusionsRepository: FusionRepository
) : ViewModel() {

    fun loadFusions(
        cardId: String,
        getFusionsThatGenerateCard: Boolean
    ) = liveData(Dispatchers.IO) {
        emit(
            if(getFusionsThatGenerateCard) {
                fusionsRepository.getAllFusionsThatGenerateCard(cardId)
            } else {
                fusionsRepository.getAllFusionsGeneratedByCard(cardId)
            }
        )
    }
}