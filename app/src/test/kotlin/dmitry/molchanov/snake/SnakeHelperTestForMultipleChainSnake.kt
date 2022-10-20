package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.domain.Direct
import dmitry.molchanov.snake.presentation.domain.SnakeChain
import dmitry.molchanov.snake.presentation.domain.SnakeHelper
import dmitry.molchanov.snake.presentation.presentation.ScreenHelperImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class SnakeHelperTestForMultipleChainSnake {

    private companion object {
        const val WIDTH = 100
        const val HEIGHT = 100
        const val CHAIN_SIZE = 10
    }

    private val snakeHelper =
        SnakeHelper(
            inputWidth = WIDTH,
            inputHeight = HEIGHT,
            inputChainSize = CHAIN_SIZE,
            screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
        )

    @Test
    fun `add chain to chains, TOP direct, simple position`() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50, y = 50 + CHAIN_SIZE)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50, y = 50 + CHAIN_SIZE * 2), newChains)
        }
    }

    @Test
    fun `add chain to chains, RIGHT direct, simple position`() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50 - CHAIN_SIZE, y = 50)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50 - CHAIN_SIZE * 2, y = 50), newChains)
        }
    }

    @Test
    fun `add chain to chains, DOWN direct, simple position`() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50, y = 50 - CHAIN_SIZE)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50, y = 50 - CHAIN_SIZE * 2), newChains)
        }
    }

    @Test
    fun `add chain to chains, LEFT direct, simple position`() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50 + CHAIN_SIZE, y = 50)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50 + CHAIN_SIZE * 2, y = 50), newChains)
        }
    }

}