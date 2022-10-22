package dmitry.molchanov.gamelogic.domain

import kotlin.random.Random

class SnakeHelper(
    inputWidth: Int,
    inputHeight: Int,
    inputChainSize: Int,
    private val screenHelper: ScreenHelper
) {

    val chainSize = getRoundedChainSize(notOptimizeChainSize = inputChainSize)
    private val maxHorizontalChains: Int = inputWidth / chainSize
    private val maxVerticalChains: Int = inputHeight / chainSize
    private val width = maxHorizontalChains * chainSize
    private val height = maxVerticalChains * chainSize

    val startChains: List<SnakeChain>
        get() = listOf(startChain)

    private val startChain: SnakeChain
        get() {
            val roundedVerticalChains =
                if (maxVerticalChains % 2 == 0) maxVerticalChains / 2
                else maxVerticalChains / 2 + 1
            return SnakeChain(x = 0, y = roundedVerticalChains * chainSize)
        }

    fun getNewChainToTail(chains: List<SnakeChain>, direct: Direct): SnakeChain =
        when (chains.size) {
            0 -> error("Zero chain")
            1 -> chains.first().getNewChainDirect(direct)
            else -> chains.getNewChainBy2LastChain()
        }

    fun getMovedChains(chains: List<SnakeChain>, direct: Direct): List<SnakeChain> {
        var lastXYPair = 0 to 0
        return chains.mapIndexed { index, snakeChain ->
            if (index == 0) {
                getNewHeadChain(currentChain = snakeChain, direct = direct)
            } else {
                SnakeChain(x = lastXYPair.first, y = lastXYPair.second)
            }.also { lastXYPair = snakeChain.x to snakeChain.y }
        }
    }

    fun isGameOver(chains: List<SnakeChain>): Boolean {
        val headChain = chains.first()
        chains.forEachIndexed { index, snakeChain ->
            if (index != 0) {
                if (snakeChain == headChain) {
                    return true
                }
            }
        }
        return false
    }

    fun getFreeChain(chains: List<SnakeChain>): SnakeChain {
        val randomHorizontalChainCount = Random.nextInt(0, maxHorizontalChains)
        val randomVerticalChainCount = Random.nextInt(0, maxVerticalChains)
        val freeChainX = randomHorizontalChainCount * chainSize
        val freeChainY = randomVerticalChainCount * chainSize
        val shouldSkip =
            !screenHelper.isPointOnScreen(
                width = width,
                height = height,
                x = freeChainX,
                y = freeChainY
            ) || chains.contains(SnakeChain(x = freeChainX, y = freeChainY))
        return if (shouldSkip) {
            getFreeChain(chains)
        } else {
            SnakeChain(x = freeChainX, y = freeChainY)
        }
    }

    private fun getRoundedChainSize(notOptimizeChainSize: Int): Int {
        val chainSizeDivRest = notOptimizeChainSize % 5
        return if (chainSizeDivRest == 0) {
            notOptimizeChainSize
        } else {
            notOptimizeChainSize + 5 - chainSizeDivRest
        }
    }

    private fun SnakeChain.getNewChainDirect(direct: Direct): SnakeChain {
        return when (direct) {
            Direct.TOP -> {
                val newY = y + chainSize
                SnakeChain(x = x, y = if (newY >= height) 0 else newY)
            }
            Direct.RIGHT -> {
                val newX = x - chainSize
                SnakeChain(x = if (newX <= 0) width - chainSize else newX, y = y)
            }
            Direct.DOWN -> {
                val newY = y - chainSize
                SnakeChain(x = x, y = if (newY <= 0) height - chainSize else newY)
            }
            Direct.LEFT -> {
                val newX = x + chainSize
                SnakeChain(x = if (newX >= width) 0 else newX, y = y)
            }
        }
    }

    private fun List<SnakeChain>.getNewChainBy2LastChain(): SnakeChain {
        val lastChain = last()
        val preLastChain = get(size - 2)
        val tailDirect = when {
            lastChain.x == preLastChain.x && lastChain.y < preLastChain.y -> Direct.DOWN
            lastChain.x == preLastChain.x && lastChain.y > preLastChain.y -> Direct.TOP
            lastChain.x > preLastChain.x && lastChain.y == preLastChain.y -> Direct.LEFT
            lastChain.x < preLastChain.x && lastChain.y == preLastChain.y -> Direct.RIGHT
            else -> error("Uncatched chain difference")
        }
        return lastChain.getNewChainDirect(tailDirect)
    }

    private fun getNewHeadChain(currentChain: SnakeChain, direct: Direct): SnakeChain {
        val x = currentChain.x
        val y = currentChain.y
        return when (direct) {
            Direct.RIGHT -> {
                val newX = x + chainSize
                SnakeChain(x = if (newX >= width) 0 else newX, y = y)
            }
            Direct.LEFT -> {
                val newX = x - chainSize
                SnakeChain(x = if (newX < 0) width else newX, y = y)
            }
            Direct.TOP -> {
                val newY = y - chainSize
                SnakeChain(x = x, y = if (newY < 0) height else newY)
            }
            Direct.DOWN -> {
                val newY = y + chainSize
                SnakeChain(x = x, y = if (newY >= height) 0 else newY)
            }
        }
    }
}
