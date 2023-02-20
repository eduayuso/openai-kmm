package dev.eduayuso.openaikmm.android.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import dev.eduayuso.openaikmm.android.navigation.Routes
import dev.eduayuso.openaikmm.features.login.ILoginViewModel
import dev.eduayuso.openaikmm.features.login.LoginContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@ExperimentalCoilApi
@Composable
fun LoginView(
    navController: NavController,
    viewModel: ILoginViewModel
) {

    val state: LoginContract.State by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val username = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.AuthSuccess -> {
                    navController.navigate(Routes.Chat.route)
                }
                is LoginContract.Effect.AuthError -> {
                    snackbarHostState.showSnackbar("Please, enter your name")
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    contentColor = Color.White,
                    backgroundColor = Color.Red
                )
            }
        }) { padding ->

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginText()
            UserTextField(username, state, viewModel)
            LoginButton(username, state, viewModel)
        }
    }
}

@Composable
fun LoginText() {

    Text(
        text = "Welcome!",
        style = TextStyle(
            fontSize = 48.sp
        ),
        modifier = Modifier.padding(top = 24.dp)
    )
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun UserTextField(
    username: MutableState<TextFieldValue>,
    state: LoginContract.State,
    viewModel: ILoginViewModel
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        label = { Text(text = "Enter your name") },
        value = username.value,
        onValueChange = {
            username.value = it
            viewModel.setEvent(
                LoginContract.Event.ValidateForm(
                    username.value.text
                )
            )
        },
        trailingIcon = {
            state.isValid?.let {
                if (it) {
                    Icon(Icons.Filled.Check, "Valid", tint = MaterialTheme.colors.primary)
                } else {
                    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginButton(

    username: MutableState<TextFieldValue>,
    state: LoginContract.State,
    viewModel: ILoginViewModel
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .padding(40.dp, 0.dp, 40.dp, 0.dp)
    ) {
        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.setEvent(
                    LoginContract.Event.Authenticate(
                        username.value.text
                    )
                )
            },
            shape = RoundedCornerShape(15.dp),
            enabled = state.isValid == true,
            modifier = Modifier
                .width(220.dp)
                .height(60.dp)
        ) {
            Text(text = "Enter")
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun LoginPreview() {

    val initialState = LoginContract.State(
        name = null,
        isValid = null
    )

    val viewModel = object: ILoginViewModel {
        override val state: StateFlow<LoginContract.State>
            get() = MutableStateFlow(initialState).asStateFlow()
        override val event: SharedFlow<LoginContract.Event>
            get() = MutableSharedFlow()
        override val effect: Flow<LoginContract.Effect>
            get() = Channel<LoginContract.Effect>().receiveAsFlow()

        override fun setEvent(event: LoginContract.Event) {

        }
    }
    LoginView(rememberNavController(), viewModel)
}