package dmitry.molchanov.snake.presentation.presentation

import android.app.Application
import dmitry.molchanov.gamelogic.di.sharedModule
import dmitry.molchanov.snake.presentation.presentation.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SnakeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SnakeApp)
            modules(
                platformModule,
                sharedModule
            )
        }
    }
}