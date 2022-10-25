package dmitry.molchanov.gamelogic.domain.gameoverstrategy

import dmitry.molchanov.gamelogic.domain.SnakeChain

object TeleportGameOverStrategy : GameOverStrategy {

    override fun isGameOver(
        prefChains: List<SnakeChain>,
        newChains: List<SnakeChain>,
        chainSize: Int,
    ): Boolean {
        val newHeadChain = newChains.first()
        val prefHeadChain = prefChains.first()
        return getDifference(prefHeadChain.x, newHeadChain.x) > chainSize ||
            getDifference(prefHeadChain.y, newHeadChain.y) > chainSize
    }

    private fun getDifference(a: Int, b: Int): Int = if (a > b) a - b else b - a
}
