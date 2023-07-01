package com.example.domain.repositories

interface OfflineCardRepository {

    suspend fun databaseHasCards(): Boolean
}