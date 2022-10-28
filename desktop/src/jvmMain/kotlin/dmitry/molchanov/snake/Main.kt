package dmitry.molchanov.snake

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dmitry.molchanov.gamelogic.GameInputParams
import dmitry.molchanov.gamelogic.di.sharedModule
import dmitry.molchanov.snake.di.desktopModule
import dmitry.molchanov.snake.ui.DesktopGameScreen
import dmitry.molchanov.snake.ui.SettingsScreen
import org.koin.core.context.startKoin

val koinApp = startKoin {
    modules(desktopModule, sharedModule)
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var gameInputParams: GameInputParams? by remember { mutableStateOf(null) }
        fun toHome() {
            gameInputParams = null
        }

        gameInputParams?.let { gameParams ->
            DesktopGameScreen(
                gameInputParams = gameParams,
                onGameFinished = ::toHome
            )
            IconButton(onClick = ::toHome) {
                Icon(Icons.Filled.Home, contentDescription = "back")
            }
        } ?: SettingsScreen { fieldSize, chainSize ->
            gameInputParams = GameInputParams(
                inputWidth = fieldSize.width,
                inputHeight = fieldSize.height,
                inputChainSize = chainSize
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
