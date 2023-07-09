package com.example.cards.model

data class UICardListFilters(
    val listType: ListType,
    val orderBy: OrderBy,
    val attackRange: Pair<Int, Int>,
    val defenseRange: Pair<Int, Int>
)

enum class ListType {
    EXPENDED, COMPACT
}

enum class OrderBy {
    NAME, ATTACK, DEFENSE
}
