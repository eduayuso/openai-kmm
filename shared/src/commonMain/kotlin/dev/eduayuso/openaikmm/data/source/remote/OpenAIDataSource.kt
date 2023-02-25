package dev.eduayuso.openaikmm.data.source.remote

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI

class OpenAIDataSource(

    private val apiClient: OpenAI,
) {

    private val maxTokens = 100
    
    suspend fun textCompletion(
        prompt: String,
        model: String
    ): TextCompletion {

        val model = ModelId(model)
        val request = CompletionRequest(
            model = model,
            prompt = prompt,
            maxTokens = maxTokens
        )
        return apiClient.completion(request)
    }
}