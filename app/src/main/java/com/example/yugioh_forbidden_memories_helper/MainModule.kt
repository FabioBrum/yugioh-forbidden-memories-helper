package com.example.yugioh_forbidden_memories_helper

import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCase
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCaseImpl
import com.example.network.OnlineCardsRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get()) }
}