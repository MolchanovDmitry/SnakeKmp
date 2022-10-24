package dmitry.molchanov.recorddsimpl

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.CoroutineDispatcher

actual class RecordSettings(context: Context, dispatcher: CoroutineDispatcher) {

    actual val settings: SuspendSettings = SharedPreferencesSettings(
        delegate = context.getSharedPreferences("RecordSettings", Context.MODE_PRIVATE)
    ).toSuspendSettings(dispatcher)
}