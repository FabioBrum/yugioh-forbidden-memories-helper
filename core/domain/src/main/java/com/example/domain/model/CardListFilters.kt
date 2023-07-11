package com.example.domain.model

data class CardListFilters(
    val listType: ListType,
    val orderBy: OrderBy,
    val attackRange: Pair<Int, Int>,
    val defenseRange: Pair<Int, Int>,
    val ordination: Ordination,
    val cardTypes: List<Type>,
    val cardNatures: List<Nature>
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
