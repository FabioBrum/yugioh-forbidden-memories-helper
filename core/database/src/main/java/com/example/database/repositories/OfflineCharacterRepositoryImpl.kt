package com.example.database.repositories

import android.content.Context
import com.example.database.Database
import com.example.database.mappers.toEntity
import com.example.domain.model.Card
import com.example.domain.model.Character
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OfflineCharacterRepository

class OfflineCharacterRepositoryImpl(
    private val context: Context
): OfflineCharacterRepository {

    private fun database() = Database.getDatabase(context)

    override suspend fun saveCharacters(characters: List<Character>) =
        database().characterDao().saveCharacters(characters.toEntity()).isNotEmpty()

}
