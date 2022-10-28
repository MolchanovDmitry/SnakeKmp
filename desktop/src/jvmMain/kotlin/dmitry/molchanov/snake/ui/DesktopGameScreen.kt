package dmitry.molchanov.snake.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dmitry.molchanov.commonui.GameScreenWrapper
import dmitry.molchanov.commonui.resolvers.ColorResolver
import dmitry.molchanov.commonui.resolvers.SizeResolver
import dmitry.molchanov.commonui.resolvers.StringResolver
import dmitry.molchanov.gamelogic.GameInputParams
import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.snake.koinApp
import org.koin.core.parameter.parametersOf

@Composable
fun DesktopGameScreen(gameInputParams: GameInputParams, onGameFinished: () -> Unit) {
    val viewModel: GameViewModel by koinApp.koin.inject { parametersOf(gameInputParams) }
    viewModel.onAction(Start)

    GameScreenWrapper(
        sizeResolver = object : SizeResolver {
            override val gameOverTextSize: TextUnit = 20.sp
        },
        colorResolver = object : ColorResolver {
            override val availableChainColors: List<Color> = listOf(Color.Red)
        },
        stringResolver = object : StringResolver {
            override val score: String = "Score"
            override val record: String = "Record"
            override val newRecord: String = "New record"
            override val gameOver: String = "Game over"
        }
    ).GameScreen(viewModel = viewModel, onGameFinished = onGameFinished)
}
