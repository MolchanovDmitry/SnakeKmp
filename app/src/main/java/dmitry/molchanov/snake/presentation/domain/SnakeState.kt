package dmitry.molchanov.snake.presentation.domain

data class SnakeState(
    val freeChain: SnakeChain,
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT,
    val chainSize: Float,
    val isGameOver: Boolean = false
)

data class SnakeChain(
    val x: Int,
    val y: Int
)

