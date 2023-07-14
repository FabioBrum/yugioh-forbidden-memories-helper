package com.example.characters.model

import android.graphics.Bitmap
import com.example.domain.model.Character

data class UICharacter(
    val name: String,
    val image: Bitmap?
)

fun Character.toUI() = UICharacter(name, image)

fun List<Character>.toUI() = map { it.toUI() }