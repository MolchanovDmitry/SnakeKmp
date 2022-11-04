package dmitry.molchanov.snake

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import dmitry.molchanov.commonui.SettingsScreen
import dmitry.molchanov.gamelogic.GameInputParams
import dmitry.molchanov.gamelogic.di.sharedModule
import dmitry.molchanov.snake.Icons.TOOLBAR
import dmitry.molchanov.snake.di.pluginModule
import dmitry.molchanov.snake.theme.WidgetTheme
import javax.swing.JComponent
import org.koin.core.context.startKoin


val koinApp = startKoin {
    modules(pluginModule, sharedModule)
}

class SnakeAction : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        DemoDialog(e.project).show()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.icon = TOOLBAR.icon
    }

    class DemoDialog(project: Project?) : DialogWrapper(project) {

        init {
            title = StringRes.appTitle
            init()
        }

        override fun createCenterPanel(): JComponent {
            return ComposePanel().apply {
                setBounds(0, 0, 800, 600)
                setContent {
                    WidgetTheme(darkTheme = true) {
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
            }
        }
    }
}
