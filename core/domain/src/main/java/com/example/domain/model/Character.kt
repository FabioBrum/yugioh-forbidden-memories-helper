package com.example.domain.model

data class Character(
    val name: String,
    val powSADropOdds: Map<String, Double>,
    val tecSADropOdds: Map<String, Double>,
    val bcdDropOdds: Map<String, Double>
)
