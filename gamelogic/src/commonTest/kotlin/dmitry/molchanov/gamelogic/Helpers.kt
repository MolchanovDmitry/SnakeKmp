package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.ScreenHelper
import kotlin.random.Random

val testScreenHelper = object : ScreenHelper {

    override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean {
        return Random.nextBoolean()
    }
}