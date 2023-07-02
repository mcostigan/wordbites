package game.words

data class Word(
    val word: String,
//    val startPosition: Pair<Int, Int>,
//    val direction: WordDirection,
    var points: Int = 0
)