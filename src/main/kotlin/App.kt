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
                    if (video in watchedVideos) {
                        setWatchedVideos(watchedVideos - video)
                        setUnwatchedVideos(unwatchedVideos + video)
                    } else {
                        setWatchedVideos(watchedVideos + video)
                        setUnwatchedVideos(unwatchedVideos - video)
                    }
                }
                watchedVideo = video in watchedVideos
            }
        }
    }
}
