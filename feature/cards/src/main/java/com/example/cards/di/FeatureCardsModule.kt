package com.example.cards.di

import com.example.cards.viewmodels.MainCardsFiltersViewModel
import com.example.cards.viewmodels.MainCardsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCardsModule = module {
    viewModel { MainCardsViewModel(get(), get()) }
    viewModel { MainCardsFiltersViewModel() }
}