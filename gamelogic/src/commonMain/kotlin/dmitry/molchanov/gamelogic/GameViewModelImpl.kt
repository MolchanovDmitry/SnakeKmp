package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.Direct.DOWN
import dmitry.molchanov.gamelogic.domain.Direct.LEFT
import dmitry.molchanov.gamelogic.domain.Direct.RIGHT
import dmitry.molchanov.gamelogic.domain.Direct.TOP
import dmitry.molchanov.gamelogic.domain.GameInProgress
import dmitry.molchanov.gamelogic.domain.GameOver
import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.SnakeState
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModelImpl(
    coroutineDispatchers: CoroutineDispatchers,
    private val snakeHelper: SnakeHelper,
    private val getCurrentRecordUseCase: GetCurrentRecordUseCase,
    private val checkScoreAndSetRecordUseCase: CheckScoreAndSetRecordUseCase
) : GameViewModel {

    private val scope = CoroutineScope(coroutineDispatchers.main + SupervisorJob())
    private val _stateFlow = MutableStateFlow(
        SnakeState(
            chainSize = snakeHelper.chainSize.toFloat(),
            freeChain = SnakeChain(x = 0, y = 0),
            chains = snakeHelper.startChains
        )
    )

    override val stateFlow = _stateFlow.asStateFlow()
    private val state: SnakeState
        get() = stateFlow.value

    private var job: Job? = null
    private var speed = START_SPEED

    init {
        scope.launch {
            initNewFreeChain()
            runSnake()
        }
    }

    override fun onAction(action: Action) {
        when (action) {
            is NewDirect -> changeDirect(action.direct)
            GameOverClick -> runNewGame()
        }
    }

    private fun runNewGame() = scope.launch {
        speed = START_SPEED
        saveNewRecord()
        initIdleGameState()
        initNewFreeChain()
    }

    private suspend fun saveNewRecord() {
        (state.gameOverStatus as? GameOver)?.score?.let { score ->
            checkScoreAndSetRecordUseCase.execute(score)
        }
    }

    private fun initIdleGameState() {
        _stateFlow.update {
            it.copy(
                direct = RIGHT,
                gameOverStatus = GameInProgress,
                chains = snakeHelper.startChains
            )
        }
    }

    private fun initNewFreeChain() {
        _stateFlow.update {
            it.copy(freeChain = snakeHelper.getFreeChain(chains = state.chains))
        }
    }

    private fun changeDirect(newDirect: Direct) {
        val currentDirect = state.direct
        val shouldUpdate = when {
            newDirect == TOP && (currentDirect == RIGHT || currentDirect == LEFT) -> true
            newDirect == RIGHT && (currentDirect == TOP || currentDirect == DOWN) -> true
            newDirect == DOWN && (currentDirect == RIGHT || currentDirect == LEFT) -> true
            newDirect == LEFT && (currentDirect == TOP || currentDirect == DOWN) -> true
            else -> false
        }
        if (shouldUpdate) {
            job?.cancel()
            _stateFlow.update { it.copy(direct = newDirect) }
            runSnake()
        }
    }

    private fun runSnake() {
        job = scope.launch {
            val state = state
            val chains = state.chains
            val movedChains =
                snakeHelper.getMovedChains(chains = chains, direct = state.direct).toMutableList()
            if (snakeHelper.isGameOver(movedChains)) {
                gameOver()
            }
            if (movedChains.first() == state.freeChain) {
                initNewFreeChain()
                snakeHelper.getNewChainToTail(chains = movedChains, direct = state.direct)
                    .let(movedChains::add)
                speed = (speed - (10F / 100F * speed)).toLong()
            }
            _stateFlow.update { it.copy(chains = movedChains) }
            delay(speed)
            runSnake()
        }
    }

    private suspend fun gameOver() {
        _stateFlow.update {
            it.copy(
                gameOverStatus = GameOver(
                    score = state.chains.size,
                    record = getCurrentRecordUseCase.execute()
                )
            )
        }
    }

    override fun release() {
        scope.cancel()
    }

    private companion object {
        const val START_SPEED = 700L
    }
}
