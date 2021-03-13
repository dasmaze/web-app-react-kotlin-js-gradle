import kotlinx.browser.document
import react.child
import react.dom.render

val unwatchedVideos: MutableList<Video> = mutableListOf(
    KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
)

val watchedVideos: MutableList<Video> = mutableListOf(
    KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
)

fun main() {
    render(document.getElementById("root")) {
        child(App) {}
    }
}