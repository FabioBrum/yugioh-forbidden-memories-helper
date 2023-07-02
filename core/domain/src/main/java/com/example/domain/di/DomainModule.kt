package com.example.domain.di

import com.example.domain.usecases.InitializeDatabaseIfNeededUseCase
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<InitializeDatabaseIfNeededUseCase> {
        InitializeDatabaseIfNeededUseCaseImpl(get(), get())
    }
}