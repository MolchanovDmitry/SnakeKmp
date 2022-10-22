package dmitry.molchanov.gamelogic.domain

data class SnakeState(
    val freeChain: SnakeChain,
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT,
    val chainSize: Float,
    val gameOverStatus: GameStatus = GameInProgress
)

data class SnakeChain(
    val x: Int,
    val y: Int
)

sealed class GameStatus
object GameInProgress : GameStatus()
data class GameOver(val score: Int, val record: Int) : GameStatus()
