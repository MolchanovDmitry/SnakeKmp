package dmitry.molchanov.snake.presentation.domain.usecase

import dmitry.molchanov.snake.presentation.domain.CoroutineDispatchers
import dmitry.molchanov.snake.presentation.domain.PreferenceRepository
import kotlinx.coroutines.withContext

class GetCurrentRecordUseCase(
    private val prefRepository: PreferenceRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun execute(): Int = withContext(coroutineDispatchers.io) {
        prefRepository.currentRecord
    }
}