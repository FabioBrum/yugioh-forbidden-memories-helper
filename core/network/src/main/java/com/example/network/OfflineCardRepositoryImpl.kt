package com.example.network

import com.example.domain.repositories.OfflineCardRepository

class OfflineCardRepositoryImpl: OfflineCardRepository {

    override suspend fun databaseHasCards(): Boolean {
        TODO("Not yet implemented")
    }
}