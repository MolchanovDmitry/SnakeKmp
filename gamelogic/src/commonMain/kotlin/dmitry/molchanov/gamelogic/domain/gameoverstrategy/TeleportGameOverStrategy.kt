package dmitry.molchanov.gamelogic.domain.gameoverstrategy

import dmitry.molchanov.gamelogic.domain.SnakeChain

class TeleportGameOverStrategy : GameOverStrategy {

    override fun isGameOver(
        prefChains: List<SnakeChain>,
        newChains: List<SnakeChain>,
        chainSize: Int,
    ): Boolean {
        val newHeadChain = newChains.first()
        val prefHeadChain = prefChains.first()
        if (getDifference(prefHeadChain.x, newHeadChain.x) > chainSize ||
            getDifference(prefHeadChain.y, newHeadChain.y) > chainSize
        ) {
            return true
        }
        return false
    }

    private fun getDifference(a: Int, b: Int): Int = if (a > b) a - b else b - a
}