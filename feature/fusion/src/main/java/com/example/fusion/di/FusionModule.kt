package com.example.fusion.di

import com.example.fusion.viewmodels.CreateFusionViewModel
import com.example.fusion.viewmodels.MainFusionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fusionModule = module {
    viewModel { CreateFusionViewModel(get(), get()) }
    viewModel { MainFusionViewModel(get()) }
}