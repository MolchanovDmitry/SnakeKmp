package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.SnakeHelper
import kotlin.random.Random
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class StartChainsTest {

    @Test
    fun check_start_chains_not_empty() {
        val snakeHelper = SnakeHelper(
            inputWidth = 100,
            inputHeight = 100,
            inputChainSize = 10,
            screenHelper = testScreenHelper,
            gameOverStrategies = emptyList()
        )
        val startChains = snakeHelper.startChains
        assertTrue(startChains.isNotEmpty())
    }

    @Test
    fun check_start_chains_in_right_coordinates() {
        val chainSize = 10
        val snakeHelper = SnakeHelper(
            inputWidth = 100,
            inputHeight = 100,
            inputChainSize = 10,
            screenHelper = testScreenHelper,
            gameOverStrategies = emptyList()
        )
        val startChains = snakeHelper.startChains
        startChains.forEach { chain ->
            assertTrue(chain.x % chainSize == 0)
            assertTrue(chain.y % chainSize == 0)
        }
    }

    @Test
    fun multiple_check_start_chains_in_right_coordinates() {
        repeat(1000) {
            val chainSize = Random.nextInt(10, 30)
            val inputWidth = Random.nextInt(100, 1000)
            val inputHeight = Random.nextInt(100, 1000)
            val snakeHelper = SnakeHelper(
                inputWidth = inputWidth,
                inputHeight = inputHeight,
                inputChainSize = chainSize,
                screenHelper = testScreenHelper,
                gameOverStrategies = emptyList()
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
