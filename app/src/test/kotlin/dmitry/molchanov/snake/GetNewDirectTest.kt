package dmitry.molchanov.snake

import dmitry.molchanov.snake.presentation.domain.Direct
import dmitry.molchanov.snake.presentation.domain.SnakeChain
import dmitry.molchanov.snake.presentation.domain.getNewDirect
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNewDirectTest {

    @Test
    fun `left press and top direct`() {
        val newDirect = getNewDirect(
            x = 10,
            y = 10,
            headDirect = Direct.TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(Direct.LEFT, newDirect)
    }

    @Test
    fun `right press and top direct`() {
        val newDirect = getNewDirect(
            x = 30,
            y = 10,
            headDirect = Direct.TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(Direct.RIGHT, newDirect)
    }

    @Test
    fun `top press and top direct`() {
        val newDirect = getNewDirect(
            x = 20,
            y = 0,
            headDirect = Direct.TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `down press and top direct`() {
        val newDirect = getNewDirect(
            x = 20,
            y = 30,
            headDirect = Direct.TOP,
            headChain = SnakeChain(x = 20, y = 10),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `top press and right direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = Direct.RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.TOP, newDirect)
    }

    @Test
    fun `right press and right direct`() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = Direct.RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `down press and right direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = Direct.RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.DOWN, newDirect)
    }

    @Test
    fun `left press and right direct`() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = Direct.RIGHT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `top press and down direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = Direct.DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `right press and down direct`() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = Direct.DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.RIGHT, newDirect)
    }

    @Test
    fun `down press and down direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = Direct.DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `left press and down direct`() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = Direct.DOWN,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.LEFT, newDirect)
    }

    @Test
    fun `top press and left direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 30,
            headDirect = Direct.LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.TOP, newDirect)
    }

    @Test
    fun `right press and left direct`() {
        val newDirect = getNewDirect(
            x = 50,
            y = 40,
            headDirect = Direct.LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }

    @Test
    fun `down press and left direct`() {
        val newDirect = getNewDirect(
            x = 40,
            y = 50,
            headDirect = Direct.LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(Direct.DOWN, newDirect)
    }

    @Test
    fun `left press and left direct`() {
        val newDirect = getNewDirect(
            x = 30,
            y = 40,
            headDirect = Direct.LEFT,
            headChain = SnakeChain(x = 40, y = 40),
        )
        assertEquals(null, newDirect)
    }
}