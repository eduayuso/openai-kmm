package dev.eduayuso.openaikmm.domain.entities

data class SettingsEntity(

    var user: String? = null,
    var model: String = OpenAIModels.DaVinci.id

): IEntity