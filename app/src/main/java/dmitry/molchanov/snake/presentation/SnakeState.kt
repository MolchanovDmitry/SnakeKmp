package dmitry.molchanov.snake.presentation

data class SnakeState(
    val chains: List<SnakeChain>,
    val direct: Direct = Direct.RIGHT
)

data class SnakeChain(
    val positionX: Int,
    val positionY: Int
)

