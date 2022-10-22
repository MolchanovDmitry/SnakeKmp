package dmitry.molchanov.snake.presentation.domain.usecase

import dmitry.molchanov.preference.RecordDataStore
import dmitry.molchanov.snake.presentation.domain.CoroutineDispatchers
import kotlinx.coroutines.withContext

class GetCurrentRecordUseCase(
    private val prefRepository: RecordDataStore,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun execute(): Int = withContext(coroutineDispatchers.io) {
        prefRepository.currentRecord
    }
}
