package dev.eduayuso.openaikmm.features.chat

import dev.eduayuso.openaikmm.domain.entities.MessageEntity
import dev.eduayuso.openaikmm.domain.entities.OpenAIModels
import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.entities.TextCompletionRequest
import dev.eduayuso.openaikmm.domain.interactors.GetSettingsUseCase
import dev.eduayuso.openaikmm.domain.interactors.GetTextCompletionUseCase
import dev.eduayuso.openaikmm.domain.interactors.type.Resource
import dev.eduayuso.openaikmm.presentation.IViewModel
import dev.eduayuso.openaikmm.presentation.ViewModel
import kotlinx.coroutines.launch

interface IChatViewModel:
    IViewModel<ChatContract.Event, ChatContract.State, ChatContract.Effect>

open class ChatViewModel(

    private val getTextCompletionUseCase: GetTextCompletionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase

    ) : IChatViewModel, ViewModel<ChatContract.Event, ChatContract.State, ChatContract.Effect>() {

    override fun createInitialState() = ChatContract.State()

    override fun handleEvent(event: ChatContract.Event) {

        when(event) {
            is ChatContract.Event.OnGetSettings -> getSettings()
            is ChatContract.Event.OnSendMessage -> sendMessage(event.text)
            is ChatContract.Event.OnReceiveMessage -> receiveMessage(event.text)
            is ChatContract.Event.OnSettingsTapped -> goToSettings()
            is ChatContract.Event.OnLogout -> logout()
        }
    }

    private fun getSettings() {

        collect(getSettingsUseCase()) { result ->
            when (result) {
                is Resource.Success -> {
                    setState {
                        copy(settings = result.data)
                    }
                    sendInitialMessage(result.data)
                }
                is Resource.Error -> setState {
                    copy(isError = true)
                }
            }
        }
    }

    private fun sendMessage(text: String) {

        addSentMessage(text)
        setLoading(true)

        val model = currentState.settings?.model!!
        val request = TextCompletionRequest(text, model)

        collect(getTextCompletionUseCase(request)) { result ->
            setLoading(false)
            when (result) {
                is Resource.Error -> setError(true)
                is Resource.Success -> setEvent(
                    ChatContract.Event.OnReceiveMessage(result.data)
                )
            }
        }
    }

    private fun receiveMessage(text: String) {

        addReceivedMessage(text)
    }

    private fun setLoading(value: Boolean) {

        if (value) {
            addMessage(
                message = MessageEntity(
                    text = "...",
                    fromOpenAi = true,
                    isLoading = true
                )
            )
        } else {
            removeLastMessage()
        }
    }

    private fun setError(value: Boolean) {

        addMessage(
            message = MessageEntity(
                fromOpenAi = true,
                isError = true
            )
        )
    }

    private fun addSentMessage(text: String) {

        addMessage(
            message = MessageEntity(
                text = text,
                fromOpenAi = false
            )
        )
    }

    private fun addReceivedMessage(text: String) {

        addMessage(
            message = MessageEntity(
                text = text.trimStart(),
                fromOpenAi = true
            )
        )
    }

    private fun addMessage(message: MessageEntity) {

        val messageList = (currentState.messageList ?: emptyList()).plus(message)
        setState {
            copy(messageList = messageList)
        }
    }

    private fun removeLastMessage() {

        val messageList = currentState.messageList?.dropLast(1) ?: emptyList()
        setState {
            copy(messageList = messageList)
        }
    }

    private fun sendInitialMessage(settings: SettingsEntity) {

        if (currentState.messageList.isNullOrEmpty()) {
            val initMessage = "Hello, my name is ${settings.user}."
            sendMessage(initMessage)
        }
    }

    private fun goToSettings() {

        setEffect {
            ChatContract.Effect.NavigateToSettings
        }
    }

    private fun logout() {

        setEffect {
            ChatContract.Effect.NavigateToLogin
        }
    }
}