package dmitry.molchanov.snake.presentation.presentation

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dmitry.molchanov.gamelogic.Start
import dmitry.molchanov.gamelogic.Stop
import dmitry.molchanov.recorddsimpl.RecordSettings
import dmitry.molchanov.snake.R
import dmitry.molchanov.snake.presentation.presentation.ui.GameScreen
import dmitry.molchanov.snake.presentation.presentation.ui.theme.SnakeTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        val widthHeightPair = getScreenWidthHeightPair()
        MainViewModelProvider(
            inputWidth = widthHeightPair.first,
            inputHeight = widthHeightPair.second,
            chainSize = resources.getDimensionPixelOffset(R.dimen.chain_size),
            recordSettings = lazy { RecordSettings(this, Dispatchers.IO) },
            screenHelper = ScreenHelperImpl(isRoundDevice = resources.configuration.isScreenRound)
        )
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
