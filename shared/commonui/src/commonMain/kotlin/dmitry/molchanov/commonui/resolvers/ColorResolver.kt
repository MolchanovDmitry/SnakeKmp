package dmitry.molchanov.commonui.resolvers

import androidx.compose.ui.graphics.Color

interface ColorResolver {

    val textColor: Color

    val availableChainColors: List<Color>
}