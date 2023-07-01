package com.example.network.di

import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCase
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCaseImpl
import com.example.network.OnlineCardsRepositoryImpl
import org.koin.dsl.module

val networkModule = module {
    single<OnlineCardsRepository> {
        OnlineCardsRepositoryImpl()
    }
}