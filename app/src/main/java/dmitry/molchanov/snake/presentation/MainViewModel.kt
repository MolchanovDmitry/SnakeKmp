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

class MainViewModel(
    private val height: Int,
    private val width: Int,
    private val chainSize: Int
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(SnakeState(chains = emptyList()))
    val stateFlow = _stateFlow.asStateFlow()

    init {
        _stateFlow.update {
            it.copy(chains = listOf(SnakeChain(positionX = 0, positionY = height / 2)))
        }
        viewModelScope.launch {
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