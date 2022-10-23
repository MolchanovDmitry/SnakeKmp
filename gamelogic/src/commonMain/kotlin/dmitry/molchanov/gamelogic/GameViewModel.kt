package dmitry.molchanov.gamelogic

import dmitry.molchanov.gamelogic.domain.Direct
import dmitry.molchanov.gamelogic.domain.SnakeState
import kotlinx.coroutines.flow.StateFlow

interface GameViewModel {
    fun onAction(action: Action)
    fun release()

    val stateFlow: StateFlow<SnakeState>
}

sealed class Action
class NewDirect(val direct: Direct) : Action()
object GameOverClick : Action()