package dev.eduayuso.openaikmm.android.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.eduayuso.openaikmm.android.navigation.Routes
import dev.eduayuso.openaikmm.domain.entities.OpenAIModels
import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.features.login.ILoginViewModel
import dev.eduayuso.openaikmm.features.login.LoginContract
import dev.eduayuso.openaikmm.features.settings.ISettingsViewModel
import dev.eduayuso.openaikmm.features.settings.SettingsContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@Composable
fun SettingsView(
    navController: NavHostController,
    viewModel: ISettingsViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SettingsContract.Effect.SettingsSaved -> {
                    snackbarHostState.showSnackbar("Changes saved successfully")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    backgroundColor = Color.LightGray,
                    contentColor = Color.Black
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            UserTextField(viewModel)
            ModelsSelector(viewModel)
            SaveButton(viewModel)
        }
    }
}


@Composable
fun UserTextField(
    viewModel: ISettingsViewModel
) {

    val state: SettingsContract.State by viewModel.state.collectAsState()

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 36.dp),
        label = { Text(text = "Change your name") },
        value = state.settings.user ?: "",
        onValueChange = {
            viewModel.setEvent(
                SettingsContract.Event.UpdateUserField(it)
            )
        },
        trailingIcon = {
            state.isUserValid?.let {
                if (it) {
                    Icon(Icons.Filled.Check, "Valid", tint = MaterialTheme.colors.primary)
                } else {
                    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
fun ModelsSelector(viewModel: ISettingsViewModel) {

    val state: SettingsContract.State by viewModel.state.collectAsState()

    val radioOptions = listOf(
        OpenAIModels.Ada,
        OpenAIModels.Babbage,
        OpenAIModels.Courie,
        OpenAIModels.DaVinci
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.settings.model) }
    Column {
        Text(
            text = "Select the OpenAPI model (GPT-3)",
            modifier = Modifier
                .padding(start = 18.dp, bottom = 18.dp, top = 18.dp)
        )
        radioOptions.forEach { model ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (model.id == selectedOption),
                        onClick = {
                            onOptionSelected(model.id)
                            viewModel.setEvent(
                                SettingsContract.Event.UpdateModelSelection(model.id)
                            )
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (model.id == selectedOption),
                    onClick = { onOptionSelected(model.id) }
                )
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.body1.merge()
                )
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaveButton(
    viewModel: ISettingsViewModel
) {

    val state: SettingsContract.State by viewModel.state.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .padding(40.dp, 0.dp, 40.dp, 0.dp)
    ) {
        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.setEvent(
                    SettingsContract.Event.SaveChanges(
                        state.settings.user ?: "",
                        state.settings.model
                    )
                )
            },
            shape = RoundedCornerShape(15.dp),
            enabled = state.isUserValid,
            modifier = Modifier
                .width(220.dp)
                .height(60.dp)
        ) {
            Text(text = "Save")
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun SettingsViewPreview() {

    val initialState = SettingsContract.State(
        settings = SettingsEntity(user = "EduTest")
    )

    val viewModel = object: ISettingsViewModel {
        override val state: StateFlow<SettingsContract.State>
            get() = MutableStateFlow(initialState).asStateFlow()
        override val event: SharedFlow<SettingsContract.Event>
            get() = MutableSharedFlow()
        override val effect: Flow<SettingsContract.Effect>
            get() = Channel<SettingsContract.Effect>().receiveAsFlow()

        override fun setEvent(event: SettingsContract.Event) {
        }
    }

    SettingsView(rememberNavController(), viewModel)
}