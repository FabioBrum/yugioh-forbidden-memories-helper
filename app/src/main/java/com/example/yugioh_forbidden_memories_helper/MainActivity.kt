package com.example.yugioh_forbidden_memories_helper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.database.di.databaseModule
import com.example.domain.di.domainModule
import com.example.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(GlobalContext.getKoinApplicationOrNull() == null) {
            startKoin {
                // Log Koin into Android logger
                androidLogger()
                // Reference Android context
                androidContext(this@MainActivity)
                // Load modules
                modules(
                    listOf(
                        domainModule,
                        networkModule,
                        databaseModule,
                        mainModule
                    )
                )
            }
        }

        setContentView(R.layout.activity_main)
    }
}