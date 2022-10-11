package dmitry.molchanov.snake.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize

private const val PRESS_SCREEN_RATIO = 0.3

@Composable
fun GameScreen(viewModel: MainViewModel) {
    val state = viewModel.stateFlow.collectAsState()
    val chainSize = state.value.chainSize-2
    DrawSnake(state.value.chains, chainSize,viewModel::onAction)
    DrawFreeChain(state.value.freeChain, chainSize)

}

@Composable
private fun DrawSnake(chains: List<SnakeChain>, chainSize: Float, onAction: (Action) -> Unit) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = { offset ->
                onTapScreen(offset = offset, size = size, onAction)
            })
        }) {
        chains.forEach {
            println(it)
            drawRect(
                color = Color.Red,
                size = Size(chainSize, chainSize),
                topLeft = Offset(x = it.x.toFloat(), y = it.y.toFloat())
            )
        }
    }
}

@Composable
private fun DrawFreeChain(freeChain: SnakeChain, chainSize: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            color = Color.Red,
            size = Size(chainSize, chainSize),
            topLeft = Offset(
                x = freeChain.x.toFloat(),
                y = freeChain.y.toFloat()
            )
        )
    }
}

private fun onTapScreen(offset: Offset, size: IntSize, onAction: (Action) -> Unit) {
    val x = offset.x.toInt()
    val y = offset.y.toInt()
    val height = size.height
    val width = size.width
    println("detect tap")
    when {
        isLeftClick(x = x, y = y, width = width, height = height) -> onAction(LeftClick)
        isRightClick(x = x, y = y, width = width, height = height) -> onAction(RightClick)
        isTopClick(x = x, y = y, width = width, height = height) -> onAction(TopClick)
        isBottomClick(x = x, y = y, width = width, height = height) -> onAction(BottomClick)
    }
}

private fun isLeftClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    x < width * PRESS_SCREEN_RATIO &&
            y in (height * PRESS_SCREEN_RATIO).toInt()..(height - (height * PRESS_SCREEN_RATIO)).toInt()

private fun isRightClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    x > width - width * PRESS_SCREEN_RATIO &&
            y in (height * PRESS_SCREEN_RATIO).toInt()..(height - (height * PRESS_SCREEN_RATIO)).toInt()

private fun isTopClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    y < height * PRESS_SCREEN_RATIO &&
            x in (width * PRESS_SCREEN_RATIO).toInt()..(width - (width * PRESS_SCREEN_RATIO)).toInt()

private fun isBottomClick(x: Int, y: Int, width: Int, height: Int): Boolean =
    y > height - height * PRESS_SCREEN_RATIO &&
            x in (width * PRESS_SCREEN_RATIO).toInt()..(width - (width * PRESS_SCREEN_RATIO)).toInt()