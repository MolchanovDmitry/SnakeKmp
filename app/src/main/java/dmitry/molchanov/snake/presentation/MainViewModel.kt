package dmitry.molchanov.snake.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dmitry.molchanov.snake.presentation.Direct.DOWN
import dmitry.molchanov.snake.presentation.Direct.LEFT
import dmitry.molchanov.snake.presentation.Direct.RIGHT
import dmitry.molchanov.snake.presentation.Direct.TOP
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
            freeChain = SnakeChain(positionX = 0, positionY = 0),
            chains = listOf(SnakeChain(positionX = 0, positionY = centerY)),
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        initNewFreeChain()
        viewModelScope.launch {
            runSnake()
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
        (x - centerX).toDouble().pow(2) + (y - centerY).toDouble().pow(2) < radius.toDouble().pow(2)

    fun onAction(action: Action) {
        println("action = $action")
        when (action) {
            TopClick -> changeDirect(newDirect = TOP)
            RightClick -> changeDirect(newDirect = RIGHT)
            BottomClick -> changeDirect(newDirect = DOWN)
            LeftClick -> changeDirect(newDirect = LEFT)
        }
    }

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

        val newChain = getNewHeadPosition(
            direct = stateFlow.value.direct,
            currentChain = stateFlow.value.chains.first()
        )
        _stateFlow.update {
            it.copy(
                chains = stateFlow.value.chains.map {
                    SnakeChain(
                        positionX = newChain.positionX,
                        positionY = newChain.positionY
                    )
                }
            )
        }
        runSnake()
    }

    private fun getNewHeadPosition(
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
        const val START_SPEED = 100L
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