package com.example.domain.repositories

import com.example.domain.model.Character

interface OfflineCharacterRepository {

    suspend fun saveCharacters(characters: List<Character>): Boolean

    suspend fun getAllCharactersWithImages(): List<Character>

    suspend fun getCharacterWithImage(characterName: String): Character?
}