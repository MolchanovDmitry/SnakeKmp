package dmitry.molchanov.snake.presentation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.Release
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.EatSelfGameOverStrategy
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val gameViewModel: GameViewModel
) : ViewModel(), GameViewModel by gameViewModel {

    override fun onCleared() {
        super.onCleared()
        gameViewModel.onAction(Release)
    }
}

class MainViewModelProvider(
    private val inputWidth: Int,
    private val inputHeight: Int,
    private val chainSize: Int,
    private val screenHelper: ScreenHelper,
    private val recordSettings: Lazy<RecordSettings>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val recordDS = lazy { RecordDataStoreImpl(recordSettings.value) }
        val coroutineDispatchers = CoroutineDispatchers(
            main = Dispatchers.Main.immediate,
            default = Dispatchers.Default,
            io = Dispatchers.IO,
            unconfined = Dispatchers.Unconfined
        )
        val gameVM = GameViewModelImpl(
            coroutineDispatchers = coroutineDispatchers,
            snakeHelper = SnakeHelper(
                inputWidth = inputWidth,
                inputHeight = inputHeight,
                inputChainSize = chainSize,
                screenHelper = screenHelper,
                gameOverStrategies = listOf(EatSelfGameOverStrategy())
            ),
            getCurrentRecordUseCase = GetCurrentRecordUseCase(recordDS.value, coroutineDispatchers),
            checkScoreAndSetRecordUseCase = CheckScoreAndSetRecordUseCase(recordDS.value, coroutineDispatchers)
        )
        return MainViewModel(gameViewModel = gameVM) as T
    }
}
