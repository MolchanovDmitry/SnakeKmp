private const val START_WIDTH = 20
private const val START_HEIGHT = 10

data class FieldState(
    val isGameStart: Boolean = false,
    val score: Int = 0,
    val record: Int = 0,
    val fieldSize: FieldSize = FieldSize(width = START_WIDTH, height = START_HEIGHT)
)

data class FieldSize(val width: Int, val height: Int)
