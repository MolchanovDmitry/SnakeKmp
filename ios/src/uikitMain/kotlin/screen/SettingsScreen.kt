package screen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.SettingsScreen(onStartClick: (IntSize, Int) -> Unit) {
    var fieldSize by remember { mutableStateOf(IntSize(0, 0)) }
    val chainSize = remember { mutableStateOf(50) }
    Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp).align(Alignment.Center).onSizeChanged {
        fieldSize = it
    }) {
        Text(
            "Current resolution = ${fieldSize.width}x${fieldSize.height}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        chainSizeRow(chainSize)
        Button(
            onClick = { onStartClick(fieldSize, chainSize.value) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Start game")
        }
    }
}

@Composable
fun ColumnScope.chainSizeRow(chainSize: MutableState<Int>) {
    Text("Select chain size:", modifier = Modifier.align(Alignment.CenterHorizontally))
    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Button(onClick = { chainSize.value -= 1 }) {
            Text("-")
        }
        Text(text = chainSize.value.toString(), modifier = Modifier.align(Alignment.CenterVertically))
        Button(onClick = { chainSize.value += 1 }) {
            Text("+")
        }
    }
}