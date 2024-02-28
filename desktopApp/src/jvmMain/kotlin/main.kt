import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.stark.compose.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainView()
    }
}