package game.board

import game.piece.Tile


class Square(private var row: Int, private var col: Int, private var tile: Tile? = null) {
    fun getPosition() = Pair(row, col)

    fun getLetter(): Char? = tile?.getLetter()
    fun setTile(tile: Tile?) {
        this.tile = tile
    }

    fun getTile(): Tile? = tile
}