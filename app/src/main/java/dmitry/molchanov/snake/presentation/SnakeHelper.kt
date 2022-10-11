package dmitry.molchanov.snake.presentation

class SnakeHelper(
    private val width: Int,
    private val height: Int,
    private val chainSize: Int
) {

    fun addChainToEnd(chains: List<SnakeChain>,direct: Direct): List<SnakeChain> =
        when (chains.size) {
            0 -> throw IllegalStateException("Zero chain")
            1 -> chains.addChainToEndByDirect(direct)
            else -> chains.addChainToEndByQueue()
        }

    private fun List<SnakeChain>.addChainToEndByDirect(direct: Direct): List<SnakeChain> {
        val chain = first()
        val newChain = when (direct) {
            Direct.TOP -> {
                val newY = chain.y + chainSize
                SnakeChain(x = chain.x, y = if (newY >= height) 0 else newY)
            }
            Direct.RIGHT -> {
                val newX = chain.x - chainSize
                SnakeChain(x = if (newX <= 0) width - chainSize else newX, y = chain.y)
            }
            Direct.DOWN -> {
                val newY = chain.y - chainSize
                SnakeChain(x = chain.x, y = if (newY <= 0) height - chainSize else newY)
            }
            Direct.LEFT -> {
                val newX = chain.x + chainSize
                SnakeChain(x = if (newX >= width) 0 else newX, y = chain.y)
            }
        }
        return listOf(chain, newChain)
    }

    private fun List<SnakeChain>.addChainToEndByQueue(): List<SnakeChain> {
        TODO("Not yet implemented")
    }
}
