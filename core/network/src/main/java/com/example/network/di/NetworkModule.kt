package com.example.network.di

import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.repositories.OnlineCharacterRepository
import com.example.network.OnlineCardsRepositoryImpl
import com.example.network.OnlineCharacterRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single<OnlineCardsRepository> {
        OnlineCardsRepositoryImpl(androidContext())
    }

    single<OnlineCharacterRepository> {
        OnlineCharacterRepositoryImpl(androidContext())
    }
}