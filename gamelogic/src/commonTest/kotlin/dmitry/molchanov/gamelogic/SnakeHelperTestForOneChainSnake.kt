package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.Direct.DOWN
import dmitry.molchanov.gamelogic.domain.Direct.LEFT
import dmitry.molchanov.gamelogic.domain.Direct.RIGHT
import dmitry.molchanov.gamelogic.domain.Direct.TOP
import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import kotlin.test.Test
import kotlin.test.assertEquals

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
        screenHelper = testScreenHelper
    )

    @Test
    fun add_chain_to_single_chain_TOP_direct_simple_position() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 40 + CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_TOP_direct_hard_position() {
        val chain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = TOP)
        val expectChain = SnakeChain(x = 40, y = 0)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_RIGHT_direct_simple_position() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = 40 - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_RIGHT_direct_hard_position() {
        val chain = SnakeChain(x = CHAIN_SIZE, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = RIGHT)
        val expectChain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_BOTTOM_direct_simple_position() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = 40 - CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_BOTTOM_direct_hard_position() {
        val chain = SnakeChain(x = 40, y = 0)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = DOWN)
        val expectChain = SnakeChain(x = 40, y = HEIGHT - CHAIN_SIZE)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_LEFT_direct_simple_position() {
        val chain = SnakeChain(x = 40, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 40 + CHAIN_SIZE, y = 40)
        assertEquals(expectChain, chains)
    }

    @Test
    fun add_chain_to_single_chain_LEFT_direct_hard_position() {
        val chain = SnakeChain(x = WIDTH - CHAIN_SIZE, y = 40)
        val chains = snakeHelper.getNewChainToTail(chains = listOf(chain), direct = LEFT)
        val expectChain = SnakeChain(x = 0, y = 40)
        assertEquals(expectChain, chains)
    }
}