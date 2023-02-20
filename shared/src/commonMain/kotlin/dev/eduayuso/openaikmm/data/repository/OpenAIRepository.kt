package dev.eduayuso.openaikmm.data.repository

import dev.eduayuso.openaikmm.data.source.remote.OpenAIDataSource
import dev.eduayuso.openaikmm.domain.repository.IOpenAIRepository

class OpenAIRepository(

    private val data: OpenAIDataSource

): IOpenAIRepository {

    override suspend fun textCompletion(prompt: String, model: String) =

        data.textCompletion(prompt, model).choices.map { it.text }
}