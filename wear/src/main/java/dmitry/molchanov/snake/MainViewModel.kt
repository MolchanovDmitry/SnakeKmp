package dmitry.molchanov.snake

import androidx.lifecycle.ViewModel
import dmitry.molchanov.gamelogic.GameViewModel
import dmitry.molchanov.gamelogic.Release

class MainViewModel(
    private val gameViewModel: GameViewModel
) : ViewModel(), GameViewModel by gameViewModel {

    override fun onCleared() {
        super.onCleared()
        gameViewModel.onAction(Release)
    }
}
