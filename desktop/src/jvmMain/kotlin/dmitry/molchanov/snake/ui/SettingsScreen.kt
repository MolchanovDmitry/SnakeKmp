package dmitry.molchanov.snake.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

private const val CHAIN_DELTA_STEP = 10
private const val DEFAULT_CHAIN_SIZE = 50

@Preview
@Composable
fun SettingsScreen(onStartGame: (IntSize, Int) -> Unit) {
    var fieldSize by remember { mutableStateOf(IntSize(0, 0)) }
    var chainSize by remember { mutableStateOf(DEFAULT_CHAIN_SIZE) }
    var chainSizeDelta by remember { mutableStateOf(0) }
    fun startGame() = onStartGame(fieldSize, chainSize + chainSizeDelta)
    Box(
        modifier = Modifier.fillMaxSize().onSizeChanged { size ->
            fieldSize = size
            chainSize = (DEFAULT_CHAIN_SIZE * (fieldSize.width.toFloat() / fieldSize.height)).toInt()
        }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Current filed size ${fieldSize.width} х ${fieldSize.height}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Enter chain size(recommended: $chainSize)",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Button(onClick = {
                    val newChainSizeDelta = chainSizeDelta - CHAIN_DELTA_STEP
                    if (chainSize + newChainSizeDelta > 0) {
                        chainSizeDelta = newChainSizeDelta
                    }
                }) {
                    Text("-")
                }
                Text(
                    text = "${chainSize + chainSizeDelta}",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(16.dp)
                )
                Button(onClick = { chainSizeDelta += CHAIN_DELTA_STEP }) {
                    Text("+")
                }
            }
            Button(onClick = ::startGame, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Start game")
            }
        }
    }
}
