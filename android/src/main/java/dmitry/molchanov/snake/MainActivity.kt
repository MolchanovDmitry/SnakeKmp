package dmitry.molchanov.snake

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.gamelogic.Stop
import dmitry.molchanov.snake.ui.GameScreen
import dmitry.molchanov.snake.ui.theme.SnakeTheme
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
                GameScreen(viewModel = viewModel)
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
