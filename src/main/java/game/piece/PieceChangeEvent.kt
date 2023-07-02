package game.piece

data class PieceChangeEvent(
    val oldPosition: Pair<Int, Int>,
    val newPosition: Pair<Int, Int>,
    val offsets: Collection<Pair<Int, Int>>
)