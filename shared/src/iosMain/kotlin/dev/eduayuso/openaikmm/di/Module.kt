package dev.eduayuso.openaikmm.di

import dev.eduayuso.openaikmm.executor.MainDispatcher
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {

    single { MainDispatcher() }
}
