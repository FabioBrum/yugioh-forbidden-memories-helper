package com.example.domain.repositories

import com.example.domain.model.Character

interface OnlineCharacterRepository {

    suspend fun getCharacterPageLinks(): List<String>

    suspend fun downloadCharacter(characterLink: String): Character?
}