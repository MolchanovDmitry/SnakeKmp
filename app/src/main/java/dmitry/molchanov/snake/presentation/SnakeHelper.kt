package dmitry.molchanov.snake.presentation

class SnakeHelper(
    private val width: Int,
    private val height: Int,
    private val chainSize: Int
) {

    fun addChainToEnd(chains: List<SnakeChain>, direct: Direct): List<SnakeChain> =
        when (chains.size) {
            0 -> error("Zero chain")
            1 -> chains.first().getNewChainDirect(direct)
            else -> chains.getNewChainBy2LastChain()
        }.let { newSnakeChain -> chains + newSnakeChain }

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
}
