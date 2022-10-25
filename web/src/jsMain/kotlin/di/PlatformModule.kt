package di

import dmitry.molchanov.gamelogic.di.CHAIN_SIZE
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.EatSelfGameOverStrategy
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.TeleportGameOverStrategy
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val platformModule = module {

    single {
        val default = Dispatchers.Default
        CoroutineDispatchers(
            main = default,
            io = default,
            default = default,
            unconfined = Dispatchers.Unconfined,
            game = default
        )
    }

    single {
        RecordSettings(dispatcher = get<CoroutineDispatchers>().io)
    }

    single { listOf(EatSelfGameOverStrategy, TeleportGameOverStrategy) }

    single<ScreenHelper> {
        object : ScreenHelper {
            override fun isPointOnScreen(width: Int, height: Int, x: Int, y: Int): Boolean =
                true
        }
    }

    single(named(CHAIN_SIZE)) {
        1
    }
}
