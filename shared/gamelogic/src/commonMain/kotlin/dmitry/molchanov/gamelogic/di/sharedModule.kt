package dmitry.molchanov.gamelogic.di

import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import dmitry.molchanov.gamelogic.domain.usecase.CheckScoreAndSetRecordUseCase
import dmitry.molchanov.gamelogic.domain.usecase.GetCurrentRecordUseCase
import dmitry.molchanov.preference.RecordDataStore
import dmitry.molchanov.recorddsimpl.RecordDataStoreImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val CHAIN_SIZE = "chain size"

val sharedModule = module {
    factory<GameViewModel> { params ->
        GameViewModelImpl(
            coroutineDispatchers = get(),
            snakeHelper = get { params },
            getCurrentRecordUseCase = get(),
            checkScoreAndSetRecordUseCase = get()
        )
    }

    factory { params ->
        SnakeHelper(
            inputWidth = params[0],
            inputHeight = params[1],
            inputChainSize = get(named(CHAIN_SIZE)),
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