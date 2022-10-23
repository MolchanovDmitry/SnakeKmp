package dmitry.molchanov.gamelogic.domain

data class SnakeState(
    val freeChain: SnakeChain,
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT,
    val chainSize: Float,
    val gameOverStatus: GameStatus = GameInProgress(record = 0)
)

data class SnakeChain(
    val x: Int,
    val y: Int
)

sealed class GameStatus(open val record: Int)
data class GameInProgress(override val record: Int) : GameStatus(record)
data class GameOver(val score: Int, override val record: Int) : GameStatus(record)
