package dmitry.molchanov.gamelogic.domain

interface ScreenHelper {
    fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean
}

val defaultScreenHelper = object : ScreenHelper {
    override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean = true
}