package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import kotlin.test.Test
import kotlin.test.assertEquals

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
            screenHelper = testScreenHelper
        )

    @Test
    fun add_chain_to_chains_TOP_direct_simple_position() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50, y = 50 + CHAIN_SIZE)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50, y = 50 + CHAIN_SIZE * 2), newChains)
        }
    }

    @Test
    fun add_chain_to_chains_RIGHT_direct_simple_position() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50 - CHAIN_SIZE, y = 50)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50 - CHAIN_SIZE * 2, y = 50), newChains)
        }
    }

    @Test
    fun add_chain_to_chains_DOWN_direct_simple_position() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50, y = 50 - CHAIN_SIZE)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50, y = 50 - CHAIN_SIZE * 2), newChains)
        }
    }

    @Test
    fun add_chain_to_chains_LEFT_direct_simple_position() {
        val firstChain = SnakeChain(x = 50, y = 50)
        val secondChain = SnakeChain(x = 50 + CHAIN_SIZE, y = 50)
        val chains = listOf(firstChain, secondChain)
        Direct.values().forEach { direct ->
            val newChains = snakeHelper.getNewChainToTail(chains, direct)
            assertEquals(SnakeChain(x = 50 + CHAIN_SIZE * 2, y = 50), newChains)
        }
    }

}