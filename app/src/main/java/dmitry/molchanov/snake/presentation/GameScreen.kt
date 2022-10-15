package dmitry.molchanov.snake.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import dmitry.molchanov.snake.R
import dmitry.molchanov.snake.presentation.theme.colors


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(viewModel: MainViewModel) {
    val state = viewModel.stateFlow.collectAsState()
    val chainSize = state.value.chainSize - 2
    val requester = remember { FocusRequester() }
    var color by remember { mutableStateOf(colors.first()) }
    fun onKeyEventFetched(keyEvent: KeyEvent): Boolean {
        if (keyEvent.type == KeyEventType.KeyUp) {
            when (keyEvent.key) {
                Key.DirectionUp -> Direct.TOP
                Key.DirectionRight -> Direct.RIGHT
                Key.DirectionDown -> Direct.DOWN
                Key.DirectionLeft -> Direct.LEFT
                else -> null
            }?.let { direct ->
                viewModel.onAction(NewDirect(direct))
                return true
            }
        }
        return false
    }

    fun onTap(offset: Offset) {
        getNewDirect(
            x = offset.x.toInt(),
            y = offset.y.toInt(),
            headDirect = state.value.direct,
            headChain = state.value.chains.first()
        )?.let{ direct ->
            viewModel.onAction(NewDirect(direct))
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onKeyEvent(::onKeyEventFetched)
        .pointerInput(Unit) {
            detectTapGestures(onDoubleTap = {
                color = getNextItem(color, colors)
            }, onTap = ::onTap)
        }
        .focusRequester(requester)
        .focusable()) {
        if (!state.value.isGameOver) {
            DrawSnake(state.value.chains, chainSize, color)
            DrawFreeChain(state.value.freeChain, color, chainSize)
        } else {
            GameOver(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { viewModel.onAction(GameOverClick) },
                score = state.value.chains.size
            )
        }
    }
    LaunchedEffect(Unit) {
        requester.requestFocus()
    }
}

@Composable
private fun GameOver(modifier: Modifier, score: Int) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.game_over),
            fontSize = dimensionResource(id = R.dimen.game_over_text_size).value.sp,
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.score, score)
        )
    }
}

@Composable
private fun DrawSnake(
    chains: List<SnakeChain>, chainSize: Float, color: Color
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        chains.forEach {
            drawRect(
                color = color,
                size = Size(chainSize, chainSize),
                topLeft = Offset(x = it.x.toFloat(), y = it.y.toFloat())
            )
        }
    }
}

@Composable
private fun DrawFreeChain(freeChain: SnakeChain, color: Color, chainSize: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            color = color, size = Size(chainSize, chainSize), topLeft = Offset(
                x = freeChain.x.toFloat(), y = freeChain.y.toFloat()
            )
        )
    }
}


private fun getNextItem(currentColor: Color, colors: Array<Color>): Color {
    val newIndex = colors.indexOf(currentColor) + 1
    return if (newIndex >= colors.size) colors.first() else colors[newIndex]
}