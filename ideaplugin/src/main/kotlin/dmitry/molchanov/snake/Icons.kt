package dmitry.molchanov.snake

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

enum class Icons(val icon: Icon) {
    TOOLBAR(icon = IconLoader.getIcon("/icons/ic_snake_tr_13x13.png", this::class.java))
}