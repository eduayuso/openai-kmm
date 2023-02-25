package dev.eduayuso.openaikmm.domain.interactors.impl

import dev.eduayuso.openaikmm.domain.entities.TextCompletionRequest
import dev.eduayuso.openaikmm.domain.interactors.GetTextCompletionUseCase
import dev.eduayuso.openaikmm.domain.repository.IOpenAIRepository

class GetTextCompletionInteractor(

    private val repository: IOpenAIRepository

): GetTextCompletionUseCase() {

    override val block: suspend (param: TextCompletionRequest) -> String
        get() = {
            val result = repository.textCompletion(it.text, it.model)
            result.joinToString(separator = "\n")
        }
}