import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.w3c.dom.Document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = Document.body ?: return
    ComposeViewport(body) {
        App()
    }
}