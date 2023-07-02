package game.piece

import game.board.Board

data class Tile internal constructor(
    private val offset: Pair<Int, Int>,
    private val letter: Char,
    private val parent: Piece
) {
    fun getOffset() = offset
    fun getLetter() = letter
    fun getPosition() = Pair(parent.getRootPosition().first + offset.first, parent.getRootPosition().second + offset.second)
    fun move(to: Pair<Int, Int>, board: Board) {
        parent.setPosition(Pair(to.first - offset.first, to.second - offset.second), board)
    }
}