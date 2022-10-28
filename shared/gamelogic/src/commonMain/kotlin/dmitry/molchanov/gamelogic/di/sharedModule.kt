package dmitry.molchanov.gamelogic.di

import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.preference.RecordDataStore
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import org.koin.dsl.module

val sharedModule = module {
    factory<GameViewModel> { params ->
        GameViewModelImpl(
            coroutineDispatchers = get(),
            snakeHelper = get { params },
            getCurrentRecordUseCase = get(),
            checkScoreAndSetRecordUseCase = get()
        )
    }

    factory {
        SnakeHelper(
            gameInputParams = get(),
            screenHelper = get(),
            gameOverStrategies = get(),
        )
    }

    factory {
        GetCurrentRecordUseCase(
            recordDataStore = get(),
            coroutineDispatchers = get()
        )
    }

    factory {
        CheckScoreAndSetRecordUseCase(
            recordDataStore = get(),
            coroutineDispatchers = get()
        )
    }

    single<RecordDataStore> { RecordDataStoreImpl(recordSettings = get()) }
}
