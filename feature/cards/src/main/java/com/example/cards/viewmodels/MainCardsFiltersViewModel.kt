package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.ListType
import com.example.domain.model.OrderBy
import com.example.domain.model.Ordination
import com.example.domain.model.CardListFilters
import com.example.domain.model.Nature
import com.example.domain.model.Type

class MainCardsFiltersViewModel: ViewModel() {

    private val _filters: MutableLiveData<CardListFilters> = MutableLiveData()
    val filters: LiveData<CardListFilters> = _filters

    fun initFilter(startFilter: CardListFilters) {
        _filters.value = startFilter
    }

    fun updateListType(newListType: ListType) {
        _filters.value = _filters.value?.copy(listType = newListType)
    }

    fun updateOrderBy(newOrderBy: OrderBy) {
        _filters.value = _filters.value?.copy(orderBy = newOrderBy)
    }

    fun updateAttackRange(min: Int, max: Int) {
        _filters.value = _filters.value?.copy(attackRange = Pair(min, max))
    }

    fun updateDefenseRange(min: Int, max: Int) {
        _filters.value = _filters.value?.copy(defenseRange = Pair(min, max))
    }

    fun updateOrdination(newOrdination: Ordination) {
        _filters.value = _filters.value?.copy(ordination = newOrdination)
    }

    fun updateCardTypes(type: Type, isChecked: Boolean) {
        val types = _filters.value?.cardTypes.orEmpty().toMutableList()
        if(isChecked) {
            types.add(type)
        } else {
            types.remove(type)
        }

        _filters.value = _filters.value?.copy(cardTypes = types)
    }

    fun updateCardNature(nature: Nature, isChecked: Boolean) {
        val natures = _filters.value?.cardNatures.orEmpty().toMutableList()
        if(isChecked) {
            natures.add(nature)
        } else {
            natures.remove(nature)
        }

        _filters.value = _filters.value?.copy(cardNatures = natures)
    }
}