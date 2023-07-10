package com.example.cards.model

import com.example.domain.model.Type

data class UICardListFilters(
    val listType: ListType,
    val orderBy: OrderBy,
    val attackRange: Pair<Int, Int>,
    val defenseRange: Pair<Int, Int>,
    val ordination: Ordination,
    val cardTypes: List<Type>
)

enum class ListType {
    EXPENDED, COMPACT
}

enum class OrderBy {
    NAME, ATTACK, DEFENSE
}

enum class Ordination {
    ASCENDING, DESCENDING
}
