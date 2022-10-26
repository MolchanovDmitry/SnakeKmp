package dmitry.molchanov.snake

import dmitry.molchanov.gamelogic.domain.ScreenHelper
import kotlin.math.min
import kotlin.math.pow

class ScreenHelperImpl(
    private val isRoundDevice: Boolean
) : ScreenHelper {

    override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean {
        if (!isRoundDevice) return true
        val centerX = width / 2
        val centerY = height / 2
        val size = min(width, height)
        return isChainInRadius(
            x = x,
            y = y,
            centerX = centerX,
            centerY = centerY,
            radius = size / 2
        )
    }

    /**
     * (x - center_x)² + (y - center_y)² < radius².
     */
    private fun isChainInRadius(x: Int, y: Int, centerX: Int, centerY: Int, radius: Int): Boolean =
        (x - centerX).toDouble().pow(2) + (y - centerY).toDouble().pow(2) < radius.toDouble().pow(2)
}
