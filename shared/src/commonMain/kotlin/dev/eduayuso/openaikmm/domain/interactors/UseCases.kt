package dev.eduayuso.openaikmm.domain.interactors

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.entities.TextCompletionRequest
import dev.eduayuso.openaikmm.domain.interactors.type.UseCaseIn
import dev.eduayuso.openaikmm.domain.interactors.type.UseCaseInOut
import dev.eduayuso.openaikmm.domain.interactors.type.UseCaseOut

abstract class LoginUseCase: UseCaseIn<String>()

abstract class GetTextCompletionUseCase: UseCaseInOut<TextCompletionRequest, String>()

abstract class SaveSettingsUseCase: UseCaseIn<SettingsEntity>()

abstract class GetSettingsUseCase: UseCaseOut<SettingsEntity>()