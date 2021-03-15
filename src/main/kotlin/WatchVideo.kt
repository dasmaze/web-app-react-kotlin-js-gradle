import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.h3
import styled.css
import styled.styledButton
import styled.styledDiv

external interface WatchProps : RProps {
    var video: Video
    var onWatchButtonPressed: (Video) -> Unit
    var watchedVideo: Boolean
}

val WatchVideo = functionalComponent<WatchProps> { props ->
    h3 {
        +"${props.video.speaker}: ${props.video.title}"
    }
    reactPlayer {
        attrs.url = props.video.videoUrl
        attrs.adNetwork = false
        attrs.noCookie = true
        attrs.title = props.video.title
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
    styledDiv {
        css {
            display = Display.flex
            marginBottom = 10.px
        }
        emailShareButton {
            attrs.url = props.video.videoUrl
            emailIcon {
                attrs.size = 32
                attrs.round = true
            }
        }
        telegramShareButton {
            attrs.url = props.video.videoUrl
            telegramIcon {
                attrs.size = 32
                attrs.round = true
            }
        }
    }
}

fun RBuilder.watchVideo(handler: WatchProps.() -> Unit): ReactElement {
    return child(WatchVideo) {
        attrs(handler)
    }
}