package dmitry.molchanov.gamelogic.domain

interface ScreenHelper {
    fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean
}
