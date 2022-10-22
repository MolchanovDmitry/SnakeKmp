package dmitry.molchanov.snake.presentation.data

import android.content.SharedPreferences
import dmitry.molchanov.preference.RecordDataStore

class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : RecordDataStore {

    override var currentRecord: Int
        get() = prefs.getInt(CURRENT_RECORD_KEY, 0)
        set(value) {
            prefs.edit().putInt(CURRENT_RECORD_KEY, value).commit()
        }

    private companion object {
        const val CURRENT_RECORD_KEY = "CURRENT_RECORD_KEY"
    }
}
