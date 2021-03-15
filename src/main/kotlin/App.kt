import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledButton
import styled.styledDiv

@JsExport
val App = functionalComponent<RProps> {
    val (currentVideo, setCurrentVideo) = useState<Video?>(null)
    val (unwatchedVideos, setUnwatchedVideos) = useState<List<Video>>(
        listOf(
            KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
        )
    )
    val (watchedVideos, setWatchedVideos) = useState<List<Video>>(
        listOf(
            KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
        )
    )

    h1 {
        +"KotlinConf Explorer"
    }
    val selectVideo: (Video) -> Unit = { video ->
        setCurrentVideo(video)
    }
    div {
        h3 {
            +"Videos to watch"
        }
        videoList {
            videos = unwatchedVideos
            selectedVideo = currentVideo
            onSelectVideo = selectVideo
        }
        h3 {
            +"Videos watched"
        }
        videoList {
            videos = watchedVideos
            selectedVideo = currentVideo
            onSelectVideo = selectVideo
        }
    }
    if (currentVideo != null) {
        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            watchVideo {
                video = currentVideo
                onWatchButtonPressed = { video ->
                    if(video in watchedVideos) {
                        setWatchedVideos(watchedVideos - video)
                        setUnwatchedVideos(unwatchedVideos + video)
                    }
                    else {
                        setWatchedVideos(watchedVideos + video)
                        setUnwatchedVideos(unwatchedVideos - video)
                    }
                }
                watchedVideo = video in watchedVideos
            }
        }
    }
}

external interface WatchProps : RProps {
    var video: Video
    var onWatchButtonPressed: (Video) -> Unit
    var watchedVideo: Boolean
}

val WatchVideo = functionalComponent<WatchProps> { props ->
    h3 {
        +"${props.video.speaker}: ${props.video.title}"
    }
    img {
        attrs {
            src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
        }
    }
    styledButton {
        css {
            display = Display.block
            backgroundColor = if(props.watchedVideo) Color.red else Color.lightGreen
        }
        attrs {
            onClickFunction = {
                props.onWatchButtonPressed(props.video)
            }
        }
        if(props.watchedVideo) {
            +"Mark as unwatched"
        }
        else {
            +"Mark as watched"
        }
    }
}

fun RBuilder.watchVideo(handler: WatchProps.() -> Unit): ReactElement {
    return child(WatchVideo) {
        attrs(handler)
    }
}

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