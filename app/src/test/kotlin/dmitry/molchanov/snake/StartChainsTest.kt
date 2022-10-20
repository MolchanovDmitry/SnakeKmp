package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.domain.SnakeHelper
import dmitry.molchanov.snake.presentation.presentation.ScreenHelperImpl
import junit.framework.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class StartChainsTest {

    @Test
    fun `check start chains not empty`() {
        val snakeHelper = SnakeHelper(
            inputWidth = 100,
            inputHeight = 100,
            inputChainSize = 10,
            screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
        )
        val startChains = snakeHelper.startChains
        assertTrue(startChains.isNotEmpty())
    }

    @Test
    fun `check start chains in right coordinates`() {
        val chainSize = 10
        val snakeHelper = SnakeHelper(
            inputWidth = 100,
            inputHeight = 100,
            inputChainSize = 10,
            screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
        )
        val startChains = snakeHelper.startChains
        startChains.forEach { chain ->
            assertTrue(chain.x % chainSize == 0)
            assertTrue(chain.y % chainSize == 0)
        }
    }

    @Test
    fun `multiple check start chains in right coordinates`() {
        repeat(1000) {
            val chainSize = Random.nextInt(10, 30)
            val inputWidth = Random.nextInt(100, 1000)
            val inputHeight = Random.nextInt(100, 1000)
            val snakeHelper = SnakeHelper(
                inputWidth = inputWidth,
                inputHeight = inputHeight,
                inputChainSize = chainSize,
                screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
            )
            val startChains = snakeHelper.startChains
            startChains.forEach { chain ->
                assertEquals(
                    "Chain size = $chainSize inputWidth = $inputWidth inputHeight = $inputHeight chainX = ${chain.x} chainY = ${chain.y}",
                    0,
                    chain.x % snakeHelper.chainSize
                )
                assertEquals(
                    "Chain size = $chainSize inputWidth = $inputWidth inputHeight = $inputHeight chainX = ${chain.x} chainY = ${chain.y}",
                    0,
                    chain.y % snakeHelper.chainSize
                )
            }
        }
    }
}