package screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import di.iosModule
import dmitry.molchanov.commonui.SettingsScreen
import dmitry.molchanov.gamelogic.GameInputParams
import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

val koinApp = startKoin {
    modules(iosModule, sharedModule)
}

@Composable
fun MainScreen() {
    val gameInputParams = remember { mutableStateOf<GameInputParams?>(null) }
    fun showSettings() {
        gameInputParams.value = null
    }
    Box(modifier = Modifier.fillMaxSize()) {
        gameInputParams.value?.let { params ->
            val viewModel: GameViewModel by koinApp.koin.inject {
                parametersOf(params)
            }
            IosGameScreen(viewModel, onGameFinished = ::showSettings)
        } ?: SettingsScreen(onStartClick = { fieldSize, chainSize ->
            gameInputParams.value = GameInputParams(
                inputWidth = fieldSize.width,
                inputHeight = fieldSize.height,
                inputChainSize = chainSize
            )
        })
    }
}
