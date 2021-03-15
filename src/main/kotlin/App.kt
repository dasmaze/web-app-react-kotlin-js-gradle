import kotlinx.browser.window
import kotlinx.coroutines.*
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
    val (unwatchedVideos, setUnwatchedVideos) = useState<List<Video>>(listOf())
    val (watchedVideos, setWatchedVideos) = useState<List<Video>>(listOf())

    useEffect(listOf()) {
        val mainScope = MainScope()
        mainScope.launch {
            val videos = fetchVideos()
            setUnwatchedVideos(videos)
        }
    }

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

suspend fun fetchVideo(id: Int): Video {
    val response = window
        .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .json()
        .await()
    return response as Video
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}