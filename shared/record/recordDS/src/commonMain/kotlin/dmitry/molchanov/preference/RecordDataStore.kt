package dmitry.molchanov.preference

interface RecordDataStore {

    suspend fun setCurrentRecord(value: Int)
    suspend fun getCurrentRecord(): Int
}
