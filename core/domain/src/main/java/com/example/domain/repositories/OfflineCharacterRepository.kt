package com.example.domain.repositories

import com.example.domain.model.Character

interface OfflineCharacterRepository {

    suspend fun saveCharacters(characters: List<Character>): Boolean
}