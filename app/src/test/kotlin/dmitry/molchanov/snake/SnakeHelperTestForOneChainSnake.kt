package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.domain.Direct.DOWN
import dmitry.molchanov.snake.presentation.domain.Direct.LEFT
import dmitry.molchanov.snake.presentation.domain.Direct.RIGHT
import dmitry.molchanov.snake.presentation.domain.Direct.TOP
import dmitry.molchanov.snake.presentation.domain.SnakeChain
import dmitry.molchanov.snake.presentation.domain.SnakeHelper
import dmitry.molchanov.snake.presentation.presentation.ScreenHelperImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class SnakeHelperTestForOneChainSnake {

    private companion object {
        const val WIDTH = 100
        const val HEIGHT = 100
        const val CHAIN_SIZE = 10
    }

    private val snakeHelper = SnakeHelper(
        inputWidth = WIDTH,
        inputHeight = HEIGHT,
        inputChainSize = CHAIN_SIZE,
        screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
    )

    @Test
    fun `add chain to single chain, TOP direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 40 + CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, TOP direct, hard position`() {
        val chain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 0)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, RIGHT direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = 40 - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, RIGHT direct, hard position`() {
        val chain = SnakeChain(x = CHAIN_SIZE, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, BOTTOM direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = 40 - CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, BOTTOM direct, hard position`() {
        val chain = SnakeChain(x = 40, y = 0)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, LEFT direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 40 + CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun `add chain to single chain, LEFT direct, hard position`() {
        val chain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 0, y = 40)
        assertEquals(expectChain, chains)
    }
}