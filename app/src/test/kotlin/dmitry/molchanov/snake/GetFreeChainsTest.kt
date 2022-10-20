package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.domain.SnakeChain
import dmitry.molchanov.snake.presentation.domain.SnakeHelper
import dmitry.molchanov.snake.presentation.presentation.ScreenHelperImpl
import org.junit.Assert.assertFalse
import org.junit.Test
import kotlin.random.Random

class GetFreeChainsTest {

    private val snakeHelper = SnakeHelper(
        inputWidth = 500,
        inputHeight = 500,
        inputChainSize = 10,
        screenHelper = ScreenHelperImpl(isRoundDevice = Random.nextBoolean())
    )

    @Test
    fun `test free chains no in horizontal snake`() {
        val snake = (1 until 45)
            .map { it * 10 }
            .map { SnakeChain(x = it, y = 250) }
        repeat(1000) {
            val freeChain = snakeHelper.getFreeChain(snake)
            assertChainNotInSnake(
                freeChain,
                snake,
                message = "Free chain = $freeChain snake = $snake"
            )
        }
    }

    @Test
    fun `test free chains no in vertical snake`() {
        val snake = (1 until 45)
            .map { it * 10 }
            .map { SnakeChain(x = 250, y = it) }
        repeat(1000) {
            val freeChain = snakeHelper.getFreeChain(snake)
            assertChainNotInSnake(
                freeChain,
                snake,
                message = "Free chain = $freeChain snake = $snake"
            )
        }
    }

    private fun assertChainNotInSnake(
        freeChain: SnakeChain,
        snake: List<SnakeChain>,
        message: String
    ) {
        val isInChain = snake.firstOrNull { it == freeChain } != null
        assertFalse(message, isInChain)
    }
}