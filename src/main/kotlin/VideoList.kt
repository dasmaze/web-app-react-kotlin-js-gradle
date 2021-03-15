import kotlinx.html.js.onClickFunction
import react.*
import react.dom.p

external interface VideoListProps : RProps {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
}

val VideoList = functionalComponent<VideoListProps> { props ->
    for (video in props.videos) {
        p {
            key = video.id.toString()
            attrs.onClickFunction = { _ -> props.onSelectVideo(video) }
            +"${if (video == props.selectedVideo) "▶ " else ""}${video.speaker}: ${video.title}"
        }
    }
}

fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement {
    return child(VideoList) {
        attrs(handler)
    }
}