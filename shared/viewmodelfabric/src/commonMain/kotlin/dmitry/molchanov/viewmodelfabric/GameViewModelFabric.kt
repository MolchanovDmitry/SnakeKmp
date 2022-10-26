package dmitry.molchanov.viewmodelfabric

import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.GameViewModelImpl
import dmitry.molchanov.gamelogic.domain.SnakeHelper
import org.koin.dsl.module


val viewModelModule = module {
    factory<GameViewModel> {
        GameViewModelImpl(
            coroutineDispatchers = get(),
            snakeHelper = get(),
            getCurrentRecordUseCase = get(),
            checkScoreAndSetRecordUseCase = get()
        )
    }

    factory { params ->
        SnakeHelper(
            inputWidth = params.get(),
            inputHeight = params.get(),
            inputChainSize = params.get(),
            screenHelper = get(),
            gameOverStrategies = get(),
        )
    }
}