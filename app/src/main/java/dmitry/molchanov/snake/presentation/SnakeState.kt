package dmitry.molchanov.snake.presentation

data class SnakeState(
    val freeChain: SnakeChain,
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT,
    val chainSize: Float
)

data class SnakeChain(
    val positionX: Int,
    val positionY: Int
)

