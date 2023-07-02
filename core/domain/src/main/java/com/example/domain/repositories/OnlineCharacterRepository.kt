package com.example.domain.repositories

import com.example.domain.model.Character

interface OnlineCharacterRepository {

    suspend fun downloadCharacters(): List<Character>
}