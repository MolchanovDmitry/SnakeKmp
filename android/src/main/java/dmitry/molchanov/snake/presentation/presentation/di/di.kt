package dmitry.molchanov.snake.presentation.presentation.di

import android.content.Context
import dmitry.molchanov.gamelogic.di.CHAIN_SIZE
import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.gamelogic.domain.ScreenHelper
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.EatSelfGameOverStrategy
import dmitry.molchanov.gamelogic.domain.gameoverstrategy.GameOverStrategy
import dmitry.molchanov.recorddsimpl.RecordSettings
import dmitry.molchanov.snake.R
import dmitry.molchanov.snake.presentation.presentation.MainViewModel
import dmitry.molchanov.snake.presentation.presentation.ScreenHelperImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val androidModule = module {

    single {
        CoroutineDispatchers(
            main = Dispatchers.Main.immediate,
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            unconfined = Dispatchers.Unconfined
        )
    }

    single {
        RecordSettings(
            context = get(),
            dispatcher = get<CoroutineDispatchers>().io
        )
    }

    viewModel { params ->
        MainViewModel(gameViewModel = get { params })
    }

    factory<List<GameOverStrategy>> { listOf(EatSelfGameOverStrategy) }

    factory<ScreenHelper> {
        ScreenHelperImpl(
            isRoundDevice = get<Context>().resources.configuration.isScreenRound
        )
    }

    factory(named(CHAIN_SIZE)) {
        get<Context>().resources.getDimensionPixelOffset(R.dimen.chain_size)
    }
}