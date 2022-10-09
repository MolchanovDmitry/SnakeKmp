package dmitry.molchanov.snake.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dmitry.molchanov.snake.presentation.Direct.DOWN
import dmitry.molchanov.snake.presentation.Direct.LEFT
import dmitry.molchanov.snake.presentation.Direct.RIGHT
import dmitry.molchanov.snake.presentation.Direct.TOP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.random.Random

class MainViewModel(
    private val height: Int,
    private val width: Int,
    private val chainSize: Int
) : ViewModel() {

    private val maxHorizontalChains: Int = width / chainSize
    private val maxVerticalChains: Int = height / chainSize
    private val centerX = chainSize * maxHorizontalChains / 2
    private val centerY = chainSize * maxVerticalChains / 2
    private val _stateFlow = MutableStateFlow(
        SnakeState(
            chainSize = chainSize.toFloat(),
            freeChain = SnakeChain(positionX = 0, positionY = 0),
            chains = listOf(
                SnakeChain(positionX = chainSize + chainSize, positionY = centerY),
                SnakeChain(positionX = chainSize, positionY = centerY),
                SnakeChain(positionX = 0, positionY = centerY),
            ),
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
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
        }
    }

    private fun initNewFreeChain() {
        val randomHorizontalChainCount = Random.nextInt(0, maxHorizontalChains)
        val randomVerticalChainCount = Random.nextInt(0, maxVerticalChains)
        val freeChainX = randomHorizontalChainCount * chainSize
        val freeChainY = randomVerticalChainCount * chainSize
        val shouldSkip = !isChainInRadius(
            x = freeChainX, y = freeChainY,
            centerX = centerX,
            centerY = centerY,
            radius = width / 2
        ) && !isChainInSnake(x = freeChainX, y = freeChainY)

        if (shouldSkip) {
            initNewFreeChain()
        } else {
            _stateFlow.update {
                it.copy(
                    freeChain = SnakeChain(positionX = freeChainX, positionY = freeChainY)
                )
            }
        }
    }

    private fun isChainInSnake(x: Int, y: Int): Boolean =
        stateFlow.value.chains
            .find { it.positionX == x && it.positionY == y } != null

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
            _stateFlow.update { it.copy(direct = newDirect) }
        }
    }

    private suspend fun runSnake() {
        delay(START_SPEED)
        val state = stateFlow.value
        val chains = stateFlow.value.chains
        val newChains = mutableListOf<SnakeChain>()
        val newChain = getNewHeadChain(
            direct = stateFlow.value.direct,
            currentChain = stateFlow.value.chains.first()
        )
        if (isNextChainFree(headChain = newChain, freeChain = state.freeChain, direct = state.direct)) {
            newChains.add(state.freeChain)
            initNewFreeChain()
        }
        newChains.add(newChain)
        var lastXYPair = 0 to 0
        chains.forEachIndexed { index, snakeChain ->
            if (index != 0) {
                newChains.add(
                    SnakeChain(positionX = lastXYPair.first, positionY = lastXYPair.second)
                )
            }
            lastXYPair = snakeChain.positionX to snakeChain.positionY
        }
        _stateFlow.update { it.copy(chains = newChains) }
        runSnake()
    }

    private fun isNextChainFree(
        headChain: SnakeChain,
        freeChain: SnakeChain,
        direct: Direct
    ): Boolean = getNewHeadChain(direct = direct, headChain) == freeChain

    private fun getNewHeadChain(
        direct: Direct,
        currentChain: SnakeChain
    ): SnakeChain {
        val x = currentChain.positionX
        val y = currentChain.positionY
        return when (direct) {
            RIGHT -> {
                val newX = x + chainSize
                SnakeChain(positionX = if (newX >= width) 0 else newX, positionY = y)
            }
            LEFT -> {
                val newX = x - chainSize
                SnakeChain(positionX = if (newX <= 0) width else newX, positionY = y)
            }
            TOP -> {
                val newY = y - chainSize
                SnakeChain(positionX = x, positionY = if (newY <= 0) height else newY)
            }
            DOWN -> {
                val newY = y + chainSize
                SnakeChain(positionX = x, positionY = if (newY >= height) 0 else newY)
            }
        }
    }

    private companion object {
        const val START_SPEED = 500L
    }
}

sealed class Action
object LeftClick : Action()
object RightClick : Action()
object TopClick : Action()
object BottomClick : Action()

class MainViewModelProvider(
    private val height: Int,
    private val width: Int,
    private val chainSize: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(height = height, width = width, chainSize = chainSize) as T
    }

}