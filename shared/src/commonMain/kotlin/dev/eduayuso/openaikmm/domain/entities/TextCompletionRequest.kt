package dev.eduayuso.openaikmm.domain.entities

data class TextCompletionRequest(

    val text: String,
    val model: String

): IEntity