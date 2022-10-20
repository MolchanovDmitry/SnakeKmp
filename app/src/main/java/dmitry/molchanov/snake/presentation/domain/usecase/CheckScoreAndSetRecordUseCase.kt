package dmitry.molchanov.snake.presentation.domain.usecase

import dmitry.molchanov.snake.presentation.domain.CoroutineDispatchers
import dmitry.molchanov.snake.presentation.domain.PreferenceRepository
import kotlinx.coroutines.withContext

class CheckScoreAndSetRecordUseCase(
    private val prefRepository: PreferenceRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun execute(score: Int): Unit = withContext(coroutineDispatchers.io) {
        if (prefRepository.currentRecord < score) {
            prefRepository.currentRecord = score
        }
    }
}
