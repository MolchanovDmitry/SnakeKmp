package screen

import DOWN_KEYCODE
import LEFT_KEYCODE
import RIGHT_KEYCODE
import TOP_KEYCODE
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.NewDirect
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.SnakeState
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.EatSelfGameOverStrategy
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.TeleportGameOverStrategy
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get

@Composable
fun DrawGame(width: Int, height: Int) {
    val gameViewModel = remember { getViewModel(width, height).apply { onAction(Start) } }
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
        Div({ style { marginTop(50.px) } }) {
            repeat(height) { rowIndex ->
                Div {
                    repeat(width) { columnIndex ->
                        Input(InputType.Radio, attrs = {
                            disabled()
                            isChecked(
                                state = state.value,
                                rowIndex = rowIndex,
                                columnIndex = columnIndex
                            ).let(::checked)
                        })
                    }
                }
            }
        }
    } else {
        GameOver(state.value)
    }
}

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
        snakeHelper = SnakeHelper(
            inputWidth = width,
            inputHeight = height,
            inputChainSize = 1,
            screenHelper = object : ScreenHelper {
                override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean =
                    true
            },
            gameOverStrategies = listOf(
                EatSelfGameOverStrategy(),
                TeleportGameOverStrategy()
            )
        ),
        checkScoreAndSetRecordUseCase = CheckScoreAndSetRecordUseCase(recordStore, dispatchers),
        getCurrentRecordUseCase = GetCurrentRecordUseCase(recordStore, dispatchers)
    )
}

@Composable
private fun ScoreView(score: Int, record: Int) {
    Text(value = "Current score: $score, top Score: $record")
}

@Composable
private fun GameOver(state: SnakeState) {
    H2 { Text("💀 Game Over with score: ${state.score} 💀") }
    if (state.score > state.record) {
        H2 { Text("Congratulations, new record!") }
    }
    Button(attrs = { onClick { window.location.reload() } }) {
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
