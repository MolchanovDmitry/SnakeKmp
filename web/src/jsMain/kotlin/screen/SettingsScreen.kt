package screen

import FieldState
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import view.CounterView

@Composable
fun Settings(
    fieldState: FieldState,
    onWidthChange: (Int) -> Unit,
    onHeightChange: (Int) -> Unit,
    onStartClick: () -> Unit
) {
    val fieldSize = fieldState.fieldSize
    Div {
        CounterView(title = "Width:", value = fieldSize.width) { newWidth ->
            onWidthChange(newWidth)
        }
    }
    Div({ style { marginTop(5.px) } }) {
        CounterView(title = "Height:", value = fieldSize.height) { newHeight ->
            onHeightChange(newHeight)
        }
    }
    Button(attrs = { onClick { onStartClick() } }) {
        Text("Start!")
    }
    repeat(fieldSize.height) {
        Div {
            repeat(fieldSize.width) {
                Input(InputType.Radio) {
                    disabled()
                }
            }
        }
    }
}
