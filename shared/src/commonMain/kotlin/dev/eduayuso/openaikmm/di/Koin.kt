package dev.eduayuso.openaikmm.di

import com.aallam.openai.client.OpenAI
import dev.eduayuso.openaikmm.data.repository.OpenAIRepository
import dev.eduayuso.openaikmm.data.repository.SettingsRepository
import dev.eduayuso.openaikmm.data.source.local.SettingsDataSource
import dev.eduayuso.openaikmm.data.source.remote.OpenAIDataSource
import dev.eduayuso.openaikmm.domain.interactors.*
import dev.eduayuso.openaikmm.domain.interactors.impl.*
import dev.eduayuso.openaikmm.domain.repository.IOpenAIRepository
import dev.eduayuso.openaikmm.domain.repository.ISettingsRepository
import dev.eduayuso.openaikmm.features.chat.ChatViewModel
import dev.eduayuso.openaikmm.features.login.LoginViewModel
import dev.eduayuso.openaikmm.features.settings.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =

    startKoin {
        appDeclaration()
        modules(
            apiModule,
            dataSourceModule,
            repositoryModule,
            useCasesModule,
            dispatcherModule,
            viewModelModule,
            platformModule()
        )
    }

fun initKoin() = initKoin {}

val apiModule = module {

    val apiKey = "[PUT YOUR OPENAI API KEY]"
    single { OpenAI(apiKey) }
}

val dataSourceModule = module {

    single { SettingsDataSource() }
    single { OpenAIDataSource(get()) }
}

val repositoryModule = module {

    single<ISettingsRepository> { SettingsRepository(get()) }
    single<IOpenAIRepository> { OpenAIRepository(get()) }
}

val useCasesModule = module {

    single<LoginUseCase> { LoginInteractor(get()) }
    single<GetSettingsUseCase> { GetSettingsInteractor(get()) }
    single<SaveSettingsUseCase> { SaveSettingsInteractor(get()) }
    single<GetTextCompletionUseCase> { GetTextCompletionInteractor(get()) }
}

val viewModelModule = module {

    single { LoginViewModel(get()) }
    single { ChatViewModel(get(), get()) }
    single { SettingsViewModel(get(), get()) }
}

val dispatcherModule = module {

    factory { Dispatchers.Default }
}