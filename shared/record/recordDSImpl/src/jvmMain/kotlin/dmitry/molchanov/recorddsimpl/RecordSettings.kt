package dmitry.molchanov.recorddsimpl

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.CoroutineDispatcher
import java.util.prefs.Preferences

actual class RecordSettings(dispatcher: CoroutineDispatcher) {

    actual val settings: SuspendSettings =
        PreferencesSettings(Preferences.userRoot())
            .toSuspendSettings(dispatcher)
}