package dmitry.molchanov.gamelogic.domain.usecase

import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.preference.RecordDataStore
import kotlinx.coroutines.withContext

class GetCurrentRecordUseCase(
    private val recordDataStore: RecordDataStore,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun execute(): Int = withContext(coroutineDispatchers.io) {
        recordDataStore.getCurrentRecord()
    }
}
