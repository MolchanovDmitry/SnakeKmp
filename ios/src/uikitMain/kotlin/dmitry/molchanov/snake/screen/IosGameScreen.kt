package dmitry.molchanov.snake.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dmitry.molchanov.commonui.GameScreenWrapper
import dmitry.molchanov.commonui.resolvers.ColorResolver
import dmitry.molchanov.commonui.resolvers.SizeResolver
import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.snake.StringRes

@Composable
fun IosGameScreen(viewModel: GameViewModel, onGameFinished: () -> Unit) {
    viewModel.onAction(Start)
    Column {
        GameScreenWrapper(
            sizeResolver = object : SizeResolver {
                override val gameOverTextSize: TextUnit = 20.sp
            },
            colorResolver = object : ColorResolver {
                override val textColor: Color = Color.Black
                override val availableChainColors: List<Color> =
                    listOf(Color(0xFF6200EE), Color.Red, Color.Green, Color.Blue)
            },
            stringResolver = StringRes
        ).GameScreen(viewModel = viewModel, onGameFinished = onGameFinished)
    }
}
