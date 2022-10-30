package dmitry.molchanov.recorddsimpl

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.CoroutineDispatcher
import platform.Foundation.NSUserDefaults

actual class RecordSettings(dispatcher: CoroutineDispatcher) {

    actual val settings: SuspendSettings =
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults).toSuspendSettings(dispatcher)
}
