package dmitry.molchanov.gamelogic.domain.usecase

import dmitry.molchanov.gamelogic.domain.CoroutineDispatchers
import dmitry.molchanov.preference.RecordDataStore
import kotlinx.coroutines.withContext

class CheckScoreAndSetRecordUseCase(
    private val recordDataStore: RecordDataStore,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun execute(score: Int): Unit = withContext(coroutineDispatchers.io) {
        if (recordDataStore.getCurrentRecord() < score) {
            recordDataStore.setCurrentRecord(score)
        }
    }
}
