package com.example.database.repositories

import android.content.Context
import android.graphics.BitmapFactory
import com.example.database.Database
import com.example.database.mappers.toDomain
import com.example.database.mappers.toEntity
import com.example.domain.model.Character
import com.example.domain.repositories.OfflineCharacterRepository
import java.io.IOException

class OfflineCharacterRepositoryImpl(
    private val context: Context
): OfflineCharacterRepository {

    private fun database() = Database.getDatabase(context)

    override suspend fun saveCharacters(characters: List<Character>) =
        database().characterDao().saveCharacters(characters.toEntity()).isNotEmpty()

    override suspend fun getAllCharactersWithImages(): List<Character> {
        val allCharacters = database().characterDao().getAllCharacters()
        return allCharacters.map {  character ->
            try {
                val characterImage =
                    context.assets.open("characterImages/${character.name}.jpeg")
                character.toDomain(
                    BitmapFactory.decodeStream(characterImage)
                )
            } catch (e: IOException) {
                character.toDomain(null)
            }
        }
    }

    override suspend fun getCharacterWithImage(characterName: String): Character? {
        val character = database().characterDao().getCharactersByName(characterName)
        return try {
            val characterImage = context.assets.open("characterImages/${character?.name}.jpeg")
            character?.toDomain(BitmapFactory.decodeStream(characterImage))
        } catch (e: IOException) {
            character?.toDomain(null)
        }
    }

}
