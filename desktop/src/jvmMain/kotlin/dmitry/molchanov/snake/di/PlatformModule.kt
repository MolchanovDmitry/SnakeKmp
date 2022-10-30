package dmitry.molchanov.snake.di

import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.defaultScreenHelper
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.EatSelfGameOverStrategy
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.TeleportGameOverStrategy
import dmitry.molchanov.recorddsimpl.RecordSettings
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val desktopModule = module {

    single {
        val default = Dispatchers.Default
        CoroutineDispatchers(
            main = default,
            io = Dispatchers.IO,
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
        defaultScreenHelper
    }
}
