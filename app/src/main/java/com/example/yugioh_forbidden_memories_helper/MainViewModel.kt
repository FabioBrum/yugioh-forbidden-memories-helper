package com.example.yugioh_forbidden_memories_helper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val initializeDatabaseIfNeededUseCase: InitializeDatabaseIfNeededUseCase
): ViewModel() {

    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            initializeDatabaseIfNeededUseCase().collect {
                Log.i("Resultado teste", it.toString())
            }
        }
    }
}