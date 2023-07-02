package com.example.domain.usecases

import kotlinx.coroutines.CoroutineScope

interface InitializeDatabaseIfNeededUseCase {

    suspend operator fun invoke(coroutineScope: CoroutineScope): Boolean
}