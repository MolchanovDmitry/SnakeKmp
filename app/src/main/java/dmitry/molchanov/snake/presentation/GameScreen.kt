package dmitry.molchanov.snake.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import dmitry.molchanov.snake.R
import dmitry.molchanov.snake.presentation.theme.colors

private const val PRESS_SCREEN_RATIO = 0.3

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
                Key.DirectionUp -> viewModel.onAction(TopClick)
                Key.DirectionRight -> viewModel.onAction(RightClick)
                Key.DirectionDown -> viewModel.onAction(BottomClick)
                Key.DirectionLeft -> viewModel.onAction(LeftClick)
                else -> null
            }?.let { return true }
        }
        return false
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onKeyEvent(::onKeyEventFetched)
        .pointerInput(Unit) {
            detectTapGestures(onDoubleTap = {
                color = getNextItem(color, colors)
            }, onTap = { offset ->
                onTapScreen(offset = offset, size = size, viewModel::onAction)
            })
        }
        .focusRequester(requester)
        .focusable()) {
        if (!state.value.isGameOver) {
            DrawSnake(state.value.chains, chainSize, color)
            DrawFreeChain(state.value.freeChain, color, chainSize)
        }else {
            GameOver(modifier = Modifier
                .align(Alignment.Center)
                .clickable { viewModel.onAction(GameOverClick) })
        }
    }
    LaunchedEffect(Unit) {
        requester.requestFocus()
    }
}

@Composable
private fun GameOver(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.game_over),
        fontSize = 20.sp,
    )
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

private fun onTapScreen(offset: Offset, size: IntSize, onAction: (Action) -> Unit) {
    val x = offset.x.toInt()
    val y = offset.y.toInt()
    val height = size.height
    val width = size.width
    when {
        isLeftClick(x = x, y = y, width = width, height = height) -> onAction(LeftClick)
        isRightClick(x = x, y = y, width = width, height = height) -> onAction(RightClick)
        isTopClick(x = x, y = y, width = width, height = height) -> onAction(TopClick)
        isBottomClick(x = x, y = y, width = width, height = height) -> onAction(BottomClick)
    }
}

private fun isLeftClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    x < width * PRESS_SCREEN_RATIO && y in (height * PRESS_SCREEN_RATIO).toInt()..(height - (height * PRESS_SCREEN_RATIO)).toInt()

private fun isRightClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    x > width - width * PRESS_SCREEN_RATIO && y in (height * PRESS_SCREEN_RATIO).toInt()..(height - (height * PRESS_SCREEN_RATIO)).toInt()

private fun isTopClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    y < height * PRESS_SCREEN_RATIO && x in (width * PRESS_SCREEN_RATIO).toInt()..(width - (width * PRESS_SCREEN_RATIO)).toInt()

private fun isBottomClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    y > height - height * PRESS_SCREEN_RATIO && x in (width * PRESS_SCREEN_RATIO).toInt()..(width - (width * PRESS_SCREEN_RATIO)).toInt()