package com.example.domain.usecases

import kotlinx.coroutines.flow.Flow

interface InitializeDatabaseIfNeededUseCase {

    suspend operator fun invoke(): Flow<Boolean>
}