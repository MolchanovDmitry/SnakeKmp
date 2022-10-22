package dmitry.molchanov.recorddsimpl

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.ObservableSettings

actual class RecordSettings(context: Context) {

    actual val settings: ObservableSettings = AndroidSettings(
        context.getSharedPreferences("RecordSettings", Context.MODE_PRIVATE)
    )
}