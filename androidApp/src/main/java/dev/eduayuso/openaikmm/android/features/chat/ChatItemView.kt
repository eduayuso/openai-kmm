package dev.eduayuso.openaikmm.android.features.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import dev.eduayuso.openaikmm.android.R
import dev.eduayuso.openaikmm.domain.entities.MessageEntity

@ExperimentalCoilApi
@Composable
fun ChatItemView(
    message: MessageEntity,
    onClick: () -> Unit
) {

    Column {

        Card(
            shape = CardShape(message),
            modifier = Modifier
                .padding(
                    start = CardStartPadding(message),
                    end = CardEndPadding(message),
                    top = 18.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth()
                .clickable(onClick = onClick),
            elevation = 2.dp,
            backgroundColor = CardColor(message)
        ) {
            Text(
                text = message.text,
                color = TextColor(message),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = AuthorRowArrangement(message),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp, start = 18.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(AuthorDrawable(message)),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .clip(CircleShape)
                    .width(18.dp)
                    .height(18.dp)
            )

            Text(
                text = AuthorName(message),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .padding(all = 0.dp)
            )
        }
    }
}

@Composable
fun CardStartPadding(message: MessageEntity): Dp {

    return when {
        message.fromOpenAi -> 18.dp
        else -> 60.dp
    }
}

@Composable
fun CardEndPadding(message: MessageEntity): Dp {

    return when {
        message.fromOpenAi -> 60.dp
        else -> 18.dp
    }
}

@Composable
fun TextColor(message: MessageEntity): Color {

    return when {
        message.isError -> Color.Red
        message.fromOpenAi -> Color.Gray
        else -> Color.Black
    }
}

@Composable
fun AuthorName(message: MessageEntity): String {

    return when {
        message.fromOpenAi -> "OpenAI"
        else -> "You"
    }
}

@Composable
fun AuthorDrawable(message: MessageEntity): Int {

    return when {
        message.fromOpenAi -> R.drawable.ic_openai
        else -> R.drawable.ic_round_person_24
    }
}

@Composable
fun AuthorRowArrangement(message: MessageEntity): Arrangement.Horizontal {

    return when {
        message.fromOpenAi -> Arrangement.Start
        else -> Arrangement.End
    }
}

@Composable
fun CardColor(message: MessageEntity): Color {

    return when {
        message.fromOpenAi -> Color.White
        else -> Color(0xFFCCCCFF)
    }
}

@Composable
fun CardShape(message: MessageEntity): Shape {

    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        message.fromOpenAi -> roundedCorners.copy(bottomStart = CornerSize(0))
        else -> roundedCorners.copy(bottomEnd = CornerSize(0))
    }
}

@Preview
@Composable
fun ChatItemViewPreview() {

    val message = MessageEntity(
        text = "Hola ke ase  Hola ke ase",
        fromOpenAi = false
    )

    ChatItemView(message) { }
}
