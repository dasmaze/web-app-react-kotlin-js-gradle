import kotlinx.css.*
import react.*
import react.dom.*
import styled.css
import styled.styledDiv

@JsExport
val App = functionalComponent<RProps>() {
    h1 {
        +"KotlinConf Explorer"
    }
    div {
        h3 {
            +"Videos to watch"
        }
        child(VideoList) {
            attrs.videos = unwatchedVideos
        }
        h3 {
            +"Videos watched"
        }
        child(VideoList) {
            attrs.videos = watchedVideos
        }
    }
    styledDiv {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }
        h3 {
            +"John Doe: Building and breaking things"
        }
        img {
            attrs {
                src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
            }
        }
    }
}

external interface VideoListProps: RProps {
    var videos: List<Video>
}

val VideoList = functionalComponent<VideoListProps> { props ->
    for (video in props.videos) {
        p {
            key = video.id.toString()
            +"${video.speaker}: ${video.title}"
        }
    }
}