package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import kotlin.test.Test
import kotlin.test.assertFalse

class GetFreeChainsTest {

    private val snakeHelper = SnakeHelper(
        inputWidth = 500,
        inputHeight = 500,
        inputChainSize = 10,
        screenHelper = testScreenHelper,
        gameOverStrategies = emptyList()
    )

    @Test
    fun test_free_chains_no_in_horizontal_snake() {
        val snake = (1 until 45)
            .map { it * 10 }
            .map { SnakeChain(x = it, y = 250) }
        repeat(1000) {
            val freeChain = snakeHelper.getFreeChain(snake)
            assertChainNotInSnake(
                freeChain,
                snake
            )
        }
    }

    @Test
    fun test_free_chains_no_in_vertical_snake() {
        val snake = (1 until 45)
            .map { it * 10 }
            .map { SnakeChain(x = 250, y = it) }
        repeat(1000) {
            val freeChain = snakeHelper.getFreeChain(snake)
            assertChainNotInSnake(
                freeChain,
                snake
            )
        }
    }

    private fun assertChainNotInSnake(
        freeChain: SnakeChain,
        snake: List<SnakeChain>
    ) {
        val isInChain = snake.firstOrNull { it == freeChain } != null
        assertFalse(isInChain)
    }
}
