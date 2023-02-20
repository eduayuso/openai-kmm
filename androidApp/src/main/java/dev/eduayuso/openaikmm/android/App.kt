package dev.eduayuso.openaikmm.android

import android.app.Application
import dev.eduayuso.openaikmm.di.KoinViewModels
import dev.eduayuso.openaikmm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

class App: Application(), KoinComponent {

    override fun onCreate() {

        super.onCreate()
        initKoin {
            androidContext(this@App)
            modules(
                module {
                    single { KoinViewModels() }
                }
            )
        }
    }
}