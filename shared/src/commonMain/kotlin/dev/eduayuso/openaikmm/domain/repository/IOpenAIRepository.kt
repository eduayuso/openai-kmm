package dev.eduayuso.openaikmm.domain.repository

interface IOpenAIRepository {

    suspend fun textCompletion(prompt: String, model: String): List<String>
}