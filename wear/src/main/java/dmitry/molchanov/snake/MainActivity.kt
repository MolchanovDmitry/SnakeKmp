package dmitry.molchanov.snake

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dmitry.molchanov.commonui.GameScreenWrapper
import dmitry.molchanov.commonui.resolvers.ColorResolver
import dmitry.molchanov.commonui.resolvers.SizeResolver
import dmitry.molchanov.commonui.resolvers.StringResolver
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.gamelogic.Stop
import dmitry.molchanov.snake.ui.GameScreen
import dmitry.molchanov.snake.ui.theme.SnakeTheme
import dmitry.molchanov.snake.ui.theme.colors
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel {
        val widthHeightPair = getScreenWidthHeightPair()
        parametersOf(widthHeightPair.first, widthHeightPair.second)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeTheme {
                GameScreenWrapper(
                    sizeResolver = object : SizeResolver {
                        override val gameOverTextSize: TextUnit =
                            dimensionResource(id = R.dimen.game_over_text_size).value.sp
                    },
                    colorResolver = object : ColorResolver {
                        override val availableChainColors: List<Color> = colors

                    },
                    stringResolver = object : StringResolver {
                        override val score: String = getString(R.string.score)
                        override val record: String = getString(R.string.new_record)
                        override val gameOver: String = getString(R.string.game_over)
                    }
                ).GameScreen(viewModel = viewModel)
                //GameScreen(viewModel = viewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onAction(Stop)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onAction(Start)
    }

    private fun getScreenWidthHeightPair(): Pair<Int, Int> {
        val height: Int
        val width: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            height = bounds.height()
            width = bounds.width()
        } else {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            height = metrics.heightPixels
            width = metrics.widthPixels
        }
        return width to height
    }
}
