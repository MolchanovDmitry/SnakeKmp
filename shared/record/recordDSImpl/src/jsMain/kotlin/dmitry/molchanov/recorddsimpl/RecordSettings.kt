package dmitry.molchanov.recorddsimpl

import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.CoroutineDispatcher

actual class RecordSettings(dispatcher: CoroutineDispatcher){
    actual val settings: SuspendSettings = StorageSettings().toSuspendSettings(dispatcher)
}