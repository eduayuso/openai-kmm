package dev.eduayuso.openaikmm.android.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import dev.eduayuso.openaikmm.android.navigation.Routes
import dev.eduayuso.openaikmm.domain.entities.MessageEntity
import dev.eduayuso.openaikmm.features.chat.ChatContract
import dev.eduayuso.openaikmm.features.chat.IChatViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@ExperimentalCoilApi
@Composable
fun ChatView(
    navController: NavHostController,
    viewModel: IChatViewModel
) {

    val state: ChatContract.State by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ChatContract.Effect.NavigateToLogin -> {
                    navController.popBackStack()
                }
                is ChatContract.Effect.NavigateToSettings -> {
                    navController.navigate(Routes.Settings.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarView(viewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color(0xFFEEEEFF))
                    .padding(bottom = 18.dp)
            ) {
                ChatContentView(
                    data = state.messageList ?: emptyList()
                )
            }
            ChatTextView(viewModel)
        }
    }
}

@Composable
fun TopBarView(viewModel: IChatViewModel) {

    TopAppBar(
        title = {
            Text("Chat with OpenAI")
        },
        actions = {
            ChatMenuView(viewModel)
        }
    )
}

@ExperimentalCoilApi
@Composable
fun ChatContentView(
    data: List<MessageEntity>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(data) { message ->
            ChatItemView(message) {
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatTextView(viewModel: IChatViewModel) {

    var inputValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(all = 12.dp),
            value = inputValue,
            onValueChange = { inputValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions {
                viewModel.setEvent(ChatContract.Event.OnSendMessage(inputValue))
                inputValue = ""
                keyboardController?.hide()
            },
        )
        Button(
            modifier = Modifier
                .height(48.dp)
                .padding(end = 12.dp),
            onClick = {
                viewModel.setEvent(ChatContract.Event.OnSendMessage(inputValue))
                inputValue = ""
                keyboardController?.hide()
            },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Preview
@Composable
fun ChatViewPreview() {

    val initialState = ChatContract.State(
        messageList = listOf(
            MessageEntity(text = "Hola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k aseHola k ase", fromOpenAi = true),
            MessageEntity(text = "Buenos días caballero", fromOpenAi = false),
            MessageEntity(text = "Soy OpenAi, pregúntame lo que te salga de la poya", fromOpenAi = true)
        )
    )

    val viewModel = object: IChatViewModel {
        override val state: StateFlow<ChatContract.State>
            get() = MutableStateFlow(initialState).asStateFlow()
        override val event: SharedFlow<ChatContract.Event>
            get() = MutableSharedFlow()
        override val effect: Flow<ChatContract.Effect>
            get() = Channel<ChatContract.Effect>().receiveAsFlow()

        override fun setEvent(event: ChatContract.Event) {
        }
    }

    ChatView(rememberNavController(), viewModel)
}