import dmitry.molchanov.gamelogic.domain.ScreenHelper

class ScreenHelperImpl : ScreenHelper {

    override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean {
        return true
    }
}
