package com.example.domain.model

import android.graphics.Bitmap

data class Card(
    val id: String,
    val name: String,
    val type: Type,
    val nature: Nature,
    val guardians: Pair<Guardian, Guardian>,
    val level: Int,
    val attack: Int,
    val defense: Int,
    val password: String,
    val starCost: Int,
    val image: Bitmap?,
)
