package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.Direct.DOWN
import dmitry.molchanov.snake.presentation.Direct.LEFT
import dmitry.molchanov.snake.presentation.Direct.RIGHT
import dmitry.molchanov.snake.presentation.Direct.TOP
import dmitry.molchanov.snake.presentation.SnakeChain
import dmitry.molchanov.snake.presentation.SnakeHelper
import org.junit.Assert.assertEquals
import org.junit.Test

class SnakeHelperTestForOneChainSnake {

    private companion object {
        const val WIDTH = 100
        const val HEIGHT = 100
        const val CHAIN_SIZE = 10
    }

    private val snakeHelper = SnakeHelper(width = WIDTH, height = HEIGHT, chainSize = CHAIN_SIZE)

    @Test
    fun `add chain to single chain, TOP direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 40 + CHAIN_SIZE)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, TOP direct, hard position`() {
        val chain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 0)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, RIGHT direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = 40 - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, RIGHT direct, hard position`() {
        val chain = SnakeChain(x = CHAIN_SIZE, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, BOTTOM direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = 40 - CHAIN_SIZE)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, BOTTOM direct, hard position`() {
        val chain = SnakeChain(x = 40, y = 0)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, LEFT direct, simple position`() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 40 + CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains.last())
    }

    @Test
    fun `add chain to single chain, LEFT direct, hard position`() {
        val chain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        val chains = snakeHelper.addChainToEnd(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 0, y = 40)
        assertEquals(expectChain, chains.last())
    }
}