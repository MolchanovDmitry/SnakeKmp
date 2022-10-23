import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dmitry.molchanov.gamelogic.GameOverClick
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.NewDirect
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.SnakeState
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get

private const val START_WIDTH = 20
private const val START_HEIGHT = 10
private const val TOP_KEYCODE = 38
private const val RIGHT_KEYCODE = 39
private const val DOWN_KEYCODE = 40
private const val LEFT_KEYCODE = 37

private data class FieldState(
    val isGameStart: Boolean = false,
    val score: Int = 0,
    val record: Int = 0,
    val fieldSize: FieldSize = FieldSize(width = START_WIDTH, height = START_HEIGHT)
)

private data class FieldSize(val width: Int, val height: Int)

private fun getViewModel(width: Int, height: Int): GameViewModelImpl {
    val dispatchers = CoroutineDispatchers(
        main = Dispatchers.Main,
        io = Dispatchers.Default,
        default = Dispatchers.Default,
        unconfined = Dispatchers.Unconfined
    )
    val recordSettings = RecordSettings(dispatcher = dispatchers.io)
    val recordStore = RecordDataStoreImpl(recordSettings)
    return GameViewModelImpl(
        coroutineDispatchers = dispatchers,
        snakeHelper = SnakeHelper(inputWidth = width,
            inputHeight = height,
            inputChainSize = 1,
            screenHelper = object : ScreenHelper {
                override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean =
                    true
            }),
        checkScoreAndSetRecordUseCase = CheckScoreAndSetRecordUseCase(recordStore, dispatchers),
        getCurrentRecordUseCase = GetCurrentRecordUseCase(recordStore, dispatchers)
    )
}

fun main() {
    renderComposable(rootElementId = "root") {
        val fieldState = remember { mutableStateOf(FieldState()) }
        val fieldSize = fieldState.value.fieldSize
        fun changeWidth(width: Int) {
            fieldState.value = fieldState.value.copy(fieldSize = fieldSize.copy(width = width))
        }

        fun changeHeight(height: Int) {
            fieldState.value = fieldState.value.copy(fieldSize = fieldSize.copy(height = height))
        }

        val isGameStart = remember { mutableStateOf(false) }
        Div(attrs = {
            style {
                property("text-align", "center")
            }
        }) {
            Header()
            Div({ style { marginTop(15.px) } }) {
                println("isGameStart = $isGameStart")
                if (isGameStart.value) {
                    DrawGame(width = fieldSize.width, height = fieldSize.height)
                } else {
                    DrawSettings(
                        fieldState = fieldState.value,
                        onWidthChange = ::changeWidth,
                        onHeightChange = ::changeHeight,
                        onStartClick = { isGameStart.value = true }
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawSettings(
    fieldState: FieldState,
    onWidthChange: (Int) -> Unit,
    onHeightChange: (Int) -> Unit,
    onStartClick: () -> Unit
) {
    val fieldSize = fieldState.fieldSize
    Div {
        CounterView(title = "Width:", value = fieldSize.width) { newWidth ->
            onWidthChange(newWidth)
        }
    }
    Div({ style { marginTop(5.px) } }) {
        CounterView(title = "Height:", value = fieldSize.height) { newHeight ->
            onHeightChange(newHeight)
        }
    }
    Button(attrs = { onClick { onStartClick() } }) {
        Text("Start!")
    }
    repeat(fieldSize.height) {
        Div {
            repeat(fieldSize.width) {
                Input(InputType.Radio)
            }
        }
    }
}

@Composable
private fun DrawGame(width: Int, height: Int) {
    val gameViewModel = remember { getViewModel(width, height) }
    val state = gameViewModel.stateFlow.collectAsState()
    val body = document.getElementsByTagName("body")[0] as HTMLElement
    body.addEventListener("keyup", {
        when ((it as? KeyboardEvent)?.keyCode) {
            TOP_KEYCODE -> Direct.TOP
            RIGHT_KEYCODE -> Direct.RIGHT
            DOWN_KEYCODE -> Direct.DOWN
            LEFT_KEYCODE -> Direct.LEFT
            else -> null
        }
            ?.let(::NewDirect)
            ?.let(gameViewModel::onAction)
    })
    ScoreView(score = state.value.score, record = state.value.record)
    if (!state.value.isGameOver) {
        repeat(height) { rowIndex ->
            Div {
                repeat(width) { columnIndex ->
                    Input(InputType.Radio, attrs = {
                        isChecked(
                            state = state.value,
                            rowIndex = rowIndex,
                            columnIndex = columnIndex
                        ).let(::checked)
                    })
                }
            }
        }
    } else {
        GameOver {
            gameViewModel.onAction(GameOverClick)
        }
    }
}

@Composable
private fun Header() {
    H1 { Text(value = "\uD83D\uDC0D Snake \uD83D\uDC0D") }
}

@Composable
private fun ScoreView(score: Int, record: Int) {
    Text(value = "Current score: $score, top Score: $record")
}

@Composable
private fun GameOver(onClick: () -> Unit) {
    H2 { Text("💀 Game Over 💀") }
    Button(attrs = {
        onClick {
            onClick()
            window.location.reload()
        }
    }) {
        Text("Try Again!")
    }
}

private fun isChecked(state: SnakeState, rowIndex: Int, columnIndex: Int): Boolean {
    val isChainInPoint = state.chains.find { snakeChain ->
        snakeChain.x == columnIndex && snakeChain.y == rowIndex
    } != null
    val isFreeChainInPoint = state.freeChain.let { freeChain ->
        freeChain.x == columnIndex && freeChain.y == rowIndex
    }
    return isChainInPoint || isFreeChainInPoint
}