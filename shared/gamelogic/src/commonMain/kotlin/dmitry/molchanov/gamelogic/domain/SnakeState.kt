package dmitry.molchanov.gamelogic.domain

const val DEFAULT_STEP_DELAY = 700L

data class SnakeState(
    val stepDelay: Long,
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
