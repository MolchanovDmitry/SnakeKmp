package dmitry.molchanov.recorddsimpl

import dmitry.molchanov.preference.RecordDataStore

class RecordDataStoreImpl(
    private val recordSettings: RecordSettings
) : RecordDataStore {

    private val settings = recordSettings.settings

    override suspend fun getCurrentRecord(): Int =
        settings.getIntOrNull(RECORD_KEY) ?: 0

    override suspend fun setCurrentRecord(value: Int) {
        settings.putInt(RECORD_KEY, value)
    }

    companion object {
        private const val RECORD_KEY = "record"
    }
}