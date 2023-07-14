package com.example.characters.di

import com.example.characters.viewmodels.CharacterDetailsViewModel
import com.example.characters.viewmodels.MainCharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCharactersModule = module {
    viewModel { MainCharactersViewModel(get()) }
    viewModel { CharacterDetailsViewModel(get(), get())}
}