@file:JsModule("react-youtube-lite")
@file:JsNonModule

import react.*

@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYouTubeProps>

external interface ReactYouTubeProps : RProps {
    var url: String
    var adNetwork: Boolean
    var title: String
    var noCookie: Boolean
}