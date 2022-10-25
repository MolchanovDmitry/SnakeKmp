package dmitry.molchanov.gamelogic.domain.gameoverstrategy

import dmitry.molchanov.gamelogic.domain.SnakeChain

class EatSelfGameOverStrategy : GameOverStrategy {

    override fun isGameOver(
        prefChains: List<SnakeChain>,
        newChains: List<SnakeChain>,
        chainSize: Int
    ): Boolean {
        val newHeadChain = newChains.first()
        newChains.forEachIndexed { index, snakeChain ->
            if (index != 0 && snakeChain == newHeadChain) {
                return true
            }
        }
        return false
    }
}
