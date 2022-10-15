package dmitry.molchanov.snake.presentation

fun getNewDirect(x: Int, y: Int, headChain: SnakeChain, headDirect: Direct): Direct? = when {
    (headDirect == Direct.TOP || headDirect == Direct.DOWN) && headChain.x < x -> Direct.RIGHT
    (headDirect == Direct.TOP || headDirect == Direct.DOWN) && headChain.x > x -> Direct.LEFT
    (headDirect == Direct.LEFT || headDirect == Direct.RIGHT) && headChain.y < y -> Direct.DOWN
    (headDirect == Direct.LEFT || headDirect == Direct.RIGHT) && headChain.y > y -> Direct.TOP
    else -> null
}
