import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import screen.DrawGame
import screen.Settings

fun main() {
    renderComposable(rootElementId = "root") {
        val fieldState = remember { mutableStateOf(FieldState()) }
        val fieldSize = fieldState.value.fieldSize
        fun changeWidth(width: Int) {
            fieldState.value = fieldState.value.copy(fieldSize = fieldSize.copy(width = width))
        }

        fun changeHeight(height: Int) {
            fieldState.value = fieldState.value.copy(fieldSize = fieldSize.copy(height = height))
        }

        val isGameStart = remember { mutableStateOf(false) }
        Div(attrs = {
            style {
                property("text-align", "center")
            }
        }) {
            Header()
            Div({ style { marginTop(15.px) } }) {
                if (isGameStart.value) {
                    DrawGame(width = fieldSize.width, height = fieldSize.height)
                } else {
                    Settings(
                        fieldState = fieldState.value,
                        onWidthChange = ::changeWidth,
                        onHeightChange = ::changeHeight,
                        onStartClick = { isGameStart.value = true }
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    H1 { Text(value = "\uD83D\uDC0D Snake \uD83D\uDC0D") }
}
