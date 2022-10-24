package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.DEFAULT_STEP_DELAY
import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.Direct.DOWN
import dmitry.molchanov.gamelogic.domain.Direct.LEFT
import dmitry.molchanov.gamelogic.domain.Direct.RIGHT
import dmitry.molchanov.gamelogic.domain.Direct.TOP
import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.SnakeState
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
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

    private val scope = CoroutineScope(coroutineDispatchers.default + SupervisorJob())
    private val _stateFlow = MutableStateFlow(
        SnakeState(
            stepDelay = DEFAULT_STEP_DELAY,
            chainSize = snakeHelper.chainSize.toFloat(),
            freeChain = SnakeChain(x = 0, y = 0),
            chains = snakeHelper.startChains,
        )
    )

    override val stateFlow = _stateFlow.asStateFlow()
    private val state: SnakeState
        get() = stateFlow.value

    init {
        scope.launch { initIdle() }
    }

    override fun onAction(action: Action) {
        when (action) {
            is NewDirect -> changeDirect(action.direct)
            Stop -> stopGame()
            Start -> startGame()
            Release -> scope.cancel()
        }
    }

    private fun stopGame() {
        scope.coroutineContext.cancelChildren()
    }

    private fun startGame() {
        scope.launch {
            if (state.isGameOver) {
                initIdle()
            }
            runSnake()
        }
    }

    private suspend fun initIdle() {
        initIdleGameState()
        initNewFreeChain()
    }

    private suspend fun initIdleGameState() {
        _stateFlow.update {
            it.copy(
                direct = RIGHT,
                isGameOver = false,
                stepDelay = DEFAULT_STEP_DELAY,
                chains = snakeHelper.startChains,
                record = getCurrentRecordUseCase.execute()
            )
        }
    }

    private fun initNewFreeChain() {
        _stateFlow.update {
            it.copy(
                score = it.chains.size,
                freeChain = snakeHelper.getFreeChain(chains = state.chains),
            )
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
            stopGame()
            _stateFlow.update { it.copy(direct = newDirect) }
            runSnake()
        }
    }

    private fun runSnake() {
        scope.launch {
            println(state.chains)
            val state = state
            val chains = state.chains
            val movedChains =
                snakeHelper.getMovedChains(chains = chains, direct = state.direct).toMutableList()
            if (snakeHelper.isGameOver(prefChains = chains, newChains = movedChains)) {
                gameOver()
            }
            val newSpeed = if (movedChains.first() == state.freeChain) {
                initNewFreeChain()
                snakeHelper.getNewChainToTail(chains = movedChains, direct = state.direct)
                    .let(movedChains::add)
                (state.stepDelay - (10F / 100F * state.stepDelay)).toLong()
            } else {
                state.stepDelay
            }
            _stateFlow.update {
                it.copy(chains = movedChains, stepDelay = newSpeed)
            }
            delay(newSpeed)
            runSnake()
        }
    }

    private suspend fun gameOver() {
        stopGame()
        _stateFlow.update { it.copy(isGameOver = true) }
        checkScoreAndSetRecordUseCase.execute(score = state.chains.size)
    }
}
