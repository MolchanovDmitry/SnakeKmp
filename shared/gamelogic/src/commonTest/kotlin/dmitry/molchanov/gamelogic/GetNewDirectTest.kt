package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.Direct.DOWN
import dmitry.molchanov.gamelogic.domain.Direct.LEFT
import dmitry.molchanov.gamelogic.domain.Direct.RIGHT
import dmitry.molchanov.gamelogic.domain.Direct.TOP
import dmitry.molchanov.gamelogic.domain.SnakeChain
import dmitry.molchanov.gamelogic.domain.getNewDirect
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNewDirectTest {

    @Test
    fun left_press_and_top_direct() {
        val newDirect = getNewDirect(
            x = 10,
            y = 10,
            headDirect = TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(LEFT, newDirect)
    }

    @Test
    fun right_press_and_top_direct() {
        val newDirect = getNewDirect(
            x = 30,
            y = 10,
            headDirect = TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(RIGHT, newDirect)
    }

    @Test
    fun top_press_and_top_direct() {
        val newDirect = getNewDirect(
            x = 20,
            y = 0,
            headDirect = TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun down_press_and_top_direct() {
        val newDirect = getNewDirect(
            x = 20,
            y = 30,
            headDirect = TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun top_press_and_right_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(TOP, newDirect)
    }

    @Test
    fun right_press_and_right_direct() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun down_press_and_right_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(DOWN, newDirect)
    }

    @Test
    fun left_press_and_right_direct() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun top_press_and_down_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun right_press_and_down_direct() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(RIGHT, newDirect)
    }

    @Test
    fun down_press_and_down_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun left_press_and_down_direct() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(LEFT, newDirect)
    }

    @Test
    fun top_press_and_left_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(TOP, newDirect)
    }

    @Test
    fun right_press_and_left_direct() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun down_press_and_left_direct() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(DOWN, newDirect)
    }

    @Test
    fun left_press_and_left_direct() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }
}