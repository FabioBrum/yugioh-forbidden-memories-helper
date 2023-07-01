package com.example.yugioh_forbidden_memories_helper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.domain.di.domainModule
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCaseImpl
import com.example.network.OnlineCardsRepositoryImpl
import com.example.network.di.networkModule
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainActivity)
            // Load modules
            modules(listOf(
                domainModule,
                networkModule,
                mainModule
            ))
        }

        setContentView(R.layout.activity_main)
    }
}