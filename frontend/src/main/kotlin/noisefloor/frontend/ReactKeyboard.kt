//package noisefloor.frontend
//import react.RBuilder
//import react.RComponent
//import react.RProps
//import react.RState
//import react.dom.div
//
//class ReactKeyboard: RComponent<ReactKeyboard.Props, RState>() {
//
//    override fun RBuilder.render() {
//        div {
//            + "React Keyboard"
//        }
//    }
//
//    interface Props: RProps {
//        var octaves: Int
//    }
//}
//
//fun RBuilder.board(octaves: Int) = child(ReactKeyboard::class) {
//    attrs.octaves = octaves
//}
