import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun CounterView(
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Div {
        Span({ style { padding(15.px) } }) {
            Text(title)
        }

        Button(attrs = { onClick { onValueChange(value - 1) } }) {
            Text("-")
        }

        Span({ style { padding(15.px) } }) {
            Text("$value")
        }

        Button(attrs = {
            onClick { onValueChange(value + 1) }
        }) {
            Text("+")
        }
    }
}
