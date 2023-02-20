package dev.eduayuso.openaikmm.domain.entities

data class MessageEntity(

    val text: String = "",
    val fromOpenAi: Boolean = true,
    val isLoading: Boolean = false,
    val isError: Boolean = false

): IEntity