package dmitry.molchanov.gamelogic.domain.gameoverstrategy

import dmitry.molchanov.gamelogic.domain.SnakeChain

interface GameOverStrategy {

    fun isGameOver(
        prefChains: List<SnakeChain>,
        newChains: List<SnakeChain>,
        chainSize: Int
    ): Boolean
}
