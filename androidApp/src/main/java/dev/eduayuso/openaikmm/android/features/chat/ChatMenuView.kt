package dev.eduayuso.openaikmm.android.features.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.features.chat.ChatContract
import dev.eduayuso.openaikmm.features.chat.IChatViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@Composable
fun ChatMenuView(viewModel: IChatViewModel) {

    val (showMenu, onShowMenuChanged) = remember { mutableStateOf(false) }

    ChatMenuViewButton(showMenu, onShowMenuChanged)
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onShowMenuChanged(false) }
    ) {
        ChatMenuViewContent(
            viewModel = viewModel
        )
    }
}

@Composable
fun ChatMenuViewButton(showMenu: Boolean, onShowMenuChanged: (Boolean) -> Unit) {

    IconButton(onClick = { onShowMenuChanged(!showMenu) }) {
        Image(
            imageVector = Icons.Filled.Menu,
            contentDescription = null,
            modifier = Modifier
                .padding(all = 8.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ChatMenuViewContent(viewModel: IChatViewModel) {

    UserRow(viewModel)
    CustomDivider()
    OptionRow(
        icon = Icons.Filled.Settings,
        text = "Settings",
        onClick = { viewModel.setEvent(ChatContract.Event.OnSettingsTapped) }
    )
    CustomDivider()
    OptionRow(
        icon = Icons.Filled.ExitToApp,
        text = "Logout",
        onClick = { viewModel.setEvent(ChatContract.Event.OnLogout) }
    )
}

@Composable
fun CustomDivider() {

    Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.LightGray)
}

@Composable
fun OptionRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    DropdownMenuItem(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onClick()
        }
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .clip(CircleShape)
                .width(18.dp)
                .height(18.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
            ),
            modifier = Modifier
                .padding(end = 36.dp)
        )
    }
}

@Composable
fun UserRow(viewModel: IChatViewModel) {

    val state: ChatContract.State by viewModel.state.collectAsState()
    val userName = "${state.settings?.user}"

    DropdownMenuItem(
        onClick = {}
    ) {
        Image(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .clip(CircleShape)
                .width(18.dp)
                .height(18.dp)
        )
        Text(
            text = userName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview
@Composable
fun ChatMenuViewPreview() {

    val initialState = ChatContract.State(
        settings = SettingsEntity(user = "Edu"),
        isError = false
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
    ChatMenuView(viewModel)
}