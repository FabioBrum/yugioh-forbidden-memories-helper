package com.example.cards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cards.model.ListType
import com.example.cards.model.OrderBy
import com.example.cards.model.UICardListFilters

class MainCardsFiltersViewModel: ViewModel() {

    private val _filters: MutableLiveData<UICardListFilters> = MutableLiveData()
    val filters: LiveData<UICardListFilters> = _filters

    fun initFilter(startFilter: UICardListFilters) {
        _filters.postValue(startFilter)
    }

    fun updateListType(newListType: ListType) {
        _filters.postValue(_filters.value?.copy(listType = newListType))
    }

    fun updateOrderBy(newOrderBy: OrderBy) {
        _filters.postValue(_filters.value?.copy(orderBy = newOrderBy))
    }

    fun updateAttackRange(min: Int, max: Int) {
        _filters.postValue(_filters.value?.copy(attackRange = Pair(min, max)))
    }

    fun updateDefenseRange(min: Int, max: Int) {
        _filters.postValue(_filters.value?.copy(defenseRange = Pair(min, max)))
    }
}