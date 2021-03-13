import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledDiv

@JsExport
val App = functionalComponent<RProps>() {
    val (currentVideo, setCurrentVideo) = useState<Video?>(null)

    h1 {
        +"KotlinConf Explorer"
    }
    div {
        h3 {
            +"Videos to watch"
        }
        videoList {
            videos = unwatchedVideos
            onClick = { video ->
                unwatchedVideos.remove(video)
                watchedVideos.add(video)
                setCurrentVideo(video)
            }
        }
        h3 {
            +"Videos watched"
        }
        videoList {
            videos = watchedVideos
            onClick = { video ->
                setCurrentVideo(video)
            }
        }
    }
    styledDiv {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }
        watchVideo {
            video = currentVideo
        }
    }
}

external interface WatchProps: RProps {
    var video: Video?
}

val WatchVideo = functionalComponent<WatchProps> { props ->
    val video = props.video
    if (video != null) {
        h3 {
            +"${video.speaker}: ${video.title}"
        }
        img {
            attrs {
                src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
            }
        }
    }
}

fun RBuilder.watchVideo(handler: WatchProps.() -> Unit): ReactElement {
    return child(WatchVideo) {
        attrs(handler)
    }
}

external interface VideoListProps: RProps {
    var videos: List<Video>
    var onClick: (Video) -> Unit
}

val VideoList = functionalComponent<VideoListProps> { props ->
    for (video in props.videos) {
        p {
            key = video.id.toString()
            attrs.onClickFunction = { _ -> props.onClick(video) }
            +"${video.speaker}: ${video.title}"
        }
    }
}

fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement {
    return child(VideoList) {
        attrs(handler)
    }
}