package dmitry.molchanov.gamelogic.domain

import kotlinx.coroutines.CoroutineDispatcher

class CoroutineDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
)
