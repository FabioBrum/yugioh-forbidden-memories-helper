package com.example.database.di

import com.example.database.repositories.OfflineCardRepositoryImpl
import com.example.domain.repositories.OfflineCardRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<OfflineCardRepository> {
        OfflineCardRepositoryImpl(androidContext())
    }
}