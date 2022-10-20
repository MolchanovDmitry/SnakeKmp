package dmitry.molchanov.snake.presentation.data

import android.content.SharedPreferences
import dmitry.molchanov.snake.presentation.domain.PreferenceRepository

class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : PreferenceRepository {

    override var currentRecord: Int
        get() = prefs.getInt(CURRENT_RECORD_KEY,0)
        set(value) {
            prefs.edit().putInt(CURRENT_RECORD_KEY, value).commit()
        }

    private companion object{
        const val CURRENT_RECORD_KEY = "CURRENT_RECORD_KEY"
    }
}