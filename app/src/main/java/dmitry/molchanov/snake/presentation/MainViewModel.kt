package dmitry.molchanov.snake.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dmitry.molchanov.snake.presentation.Direct.DOWN
import dmitry.molchanov.snake.presentation.Direct.LEFT
import dmitry.molchanov.snake.presentation.Direct.RIGHT
import dmitry.molchanov.snake.presentation.Direct.TOP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.math.pow
import kotlin.random.Random

class MainViewModel(
    inputWidth: Int,
    inputHeight: Int,
    private val chainSize: Int
) : ViewModel() {

    private val maxHorizontalChains: Int = inputWidth / chainSize
    private val maxVerticalChains: Int = inputHeight / chainSize
    private val width = maxHorizontalChains * chainSize
    private val height = maxVerticalChains * chainSize
    private val centerX = width / 2
    private val centerY = height / 2
    private val snakeHelper =
        SnakeHelper(width = width, height = height, chainSize = chainSize)
    private val scope =
        CoroutineScope(newSingleThreadContext("Snake move thread") + SupervisorJob())
    private val _stateFlow = MutableStateFlow(
        SnakeState(
            chainSize = chainSize.toFloat(),
            freeChain = SnakeChain(x = 0, y = 0),
            chains = listOf(SnakeChain(x = 0, y = centerY))
        )
    )
    val stateFlow = _stateFlow.asStateFlow()
    private var job: Job? = null
    private var speed = START_SPEED

    init {
        scope.launch {
            initNewFreeChain()
            runSnake()
        }
    }

    fun onAction(action: Action) {
        println("action = $action")
        when (action) {
            TopClick -> changeDirect(newDirect = TOP)
            RightClick -> changeDirect(newDirect = RIGHT)
            BottomClick -> changeDirect(newDirect = DOWN)
            LeftClick -> changeDirect(newDirect = LEFT)
            GameOverClick -> runNewGame()
            else -> error("Uncatched event")
        }
    }

    private fun runNewGame() = scope.launch {
        speed = START_SPEED
        _stateFlow.update {
            it.copy(
                direct = RIGHT,
                isGameOver = false,
                chains = listOf(SnakeChain(x = 0, y = centerY)),
            )
        }
        initNewFreeChain()
    }

    private fun initNewFreeChain() {
        val randomHorizontalChainCount = Random.nextInt(0, maxHorizontalChains)
        val randomVerticalChainCount = Random.nextInt(0, maxVerticalChains)
        val freeChainX = randomHorizontalChainCount * chainSize
        val freeChainY = randomVerticalChainCount * chainSize
        val shouldSkip = !isChainInRadius(
            x = freeChainX, y = freeChainY, centerX = centerX, centerY = centerY, radius = width / 2
        ) && !isChainInSnake(SnakeChain(x = freeChainX, y = freeChainY))

        if (shouldSkip) {
            initNewFreeChain()
        } else {
            _stateFlow.update { it.copy(freeChain = SnakeChain(x = freeChainX, y = freeChainY)) }
        }
    }

    private fun isChainInSnake(snakeChain: SnakeChain): Boolean =
        stateFlow.value.chains.find { it.x == snakeChain.x && it.y == snakeChain.y } != null

    /**
     * (x - center_x)² + (y - center_y)² < radius².
     */
    private fun isChainInRadius(x: Int, y: Int, centerX: Int, centerY: Int, radius: Int): Boolean =
        (x - centerX).toDouble().pow(2) + (y - centerY).toDouble().pow(2) < radius.toDouble()
            .pow(2) - 50

    private fun changeDirect(newDirect: Direct) {
        val currentDirect = stateFlow.value.direct
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
            val state = stateFlow.value
            val chains = state.chains
            val movedChains =
                snakeHelper.getMovedChains(chains = chains, direct = state.direct).toMutableList()
            if (snakeHelper.isGameOver(movedChains)) {
                _stateFlow.update { it.copy(isGameOver = true) }
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

    private companion object {
        const val START_SPEED = 700L
    }
}

sealed class Action
object LeftClick : Action()
object RightClick : Action()
object TopClick : Action()
object BottomClick : Action()
object GameOverClick : Action()

class MainViewModelProvider(
    private val width: Int, private val height: Int, private val chainSize: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(inputWidth = width, inputHeight = height, chainSize = chainSize) as T
    }

}