import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

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
        snakeHelper = SnakeHelper(
            inputWidth = 500,
            inputHeight = 500,
            inputChainSize = 10,
            screenHelper = object : ScreenHelper {
                override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean =
                    true
            }),
        checkScoreAndSetRecordUseCase = CheckScoreAndSetRecordUseCase(recordStore, dispatchers),
        getCurrentRecordUseCase = GetCurrentRecordUseCase(recordStore, dispatchers)
    )
    renderComposable(rootElementId = "root") {
        Text("hello world")
    }
}