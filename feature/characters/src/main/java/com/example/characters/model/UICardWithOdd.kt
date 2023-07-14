package com.example.characters.model

data class UICardWithOdd(
    val cardId: String,
    val cardName: String,
    val cardAttributes: String,
    val cardOdds: Double
)

enum class OddType {
    POW_SA, TEC_SA, POW_TEC_BCD
}