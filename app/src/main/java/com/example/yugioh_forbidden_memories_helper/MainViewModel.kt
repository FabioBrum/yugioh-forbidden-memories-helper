package com.example.yugioh_forbidden_memories_helper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(): ViewModel() {

    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Downloading card time","Download process started at ${Calendar.getInstance().time}")
            Log.i("Downloading card time","Download process finished at ${Calendar.getInstance().time}")
        }
    }
}