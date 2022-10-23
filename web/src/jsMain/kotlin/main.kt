import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get

const val WIDTH = 40
const val HEIGHT = 20

fun main() {
    val dispatchers = CoroutineDispatchers(
        main = Dispatchers.Main,
        io = Dispatchers.Default,
        default = Dispatchers.Default,
        unconfined = Dispatchers.Unconfined
    )
    val recordSettings = RecordSettings(dispatcher = dispatchers.io)
    val recordStore = RecordDataStoreImpl(recordSettings)
    val gameViewModel = GameViewModelImpl(
        coroutineDispatchers = dispatchers,
        snakeHelper = SnakeHelper(inputWidth = WIDTH,
            inputHeight = HEIGHT,
            inputChainSize = 1,
            screenHelper = object : ScreenHelper {
                override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean =
                    true
            }),
        checkScoreAndSetRecordUseCase = CheckScoreAndSetRecordUseCase(recordStore, dispatchers),
        getCurrentRecordUseCase = GetCurrentRecordUseCase(recordStore, dispatchers)
    )

    val body = document.getElementsByTagName("body")[0] as HTMLElement
    body.addEventListener("keyup", {
        when ((it as? KeyboardEvent)?.keyCode) {
            38 -> Direct.TOP
            39 -> Direct.RIGHT
            40 -> Direct.DOWN
            37 -> Direct.LEFT
            else -> null
        }?.let(::NewDirect)?.let(gameViewModel::onAction)
    })

    renderComposable(rootElementId = "root") {
        val state = gameViewModel.stateFlow.collectAsState()

        Div(attrs = {
            style {
                marginTop(30.px)
            }
        }) {
            repeat(HEIGHT) { rowIndex ->
                Div {
                    repeat(WIDTH) { columnIndex ->
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
        }
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