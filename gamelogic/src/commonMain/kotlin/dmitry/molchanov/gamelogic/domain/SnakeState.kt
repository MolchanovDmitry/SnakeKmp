package dmitry.molchanov.gamelogic.domain

data class SnakeState(
    val freeChain: SnakeChain,
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT,
    val chainSize: Float,
    val score: Int = 0,
    val record: Int = 0,
    val isGameOver: Boolean = false
)

data class SnakeChain(
    val x: Int,
    val y: Int
)

sealed class GameStatus(open val record: Int)
data class GameInProgress(override val record: Int) : GameStatus(record)
data class GameOver(val score: Int, override val record: Int) : GameStatus(record)
