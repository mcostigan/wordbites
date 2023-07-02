package game.board

import game.piece.Piece
import game.piece.Tile

abstract class Board(private var height: Int, private var width: Int) {
    private var pieces: MutableCollection<Piece> = mutableListOf()

    abstract fun getSquare(position: Pair<Int, Int>): Square
    abstract fun getLetter(position: Pair<Int, Int>): Char?
    abstract fun setTile(position: Pair<Int, Int>, tile: Tile?)
    abstract fun getTile(position: Pair<Int, Int>): Tile?
    open fun addPiece(piece: Piece) {
        pieces.add(piece)
    }

    fun getDimensions() = Pair(height, width)
    fun getPieces(): Collection<Piece> = pieces.toList()

}

class BoardGrid(height: Int, width: Int) : Board(height, width) {
    private var data = MutableList(height) { row ->
        MutableList(
            width
        ) { col -> Square(row, col) }
    }

    override fun getSquare(position: Pair<Int, Int>): Square = data[position.first][position.second]


    override fun getLetter(position: Pair<Int, Int>): Char? = try {
        getTile(position)?.getLetter()
    } catch (e: IndexOutOfBoundsException) {
        null
    }


    override fun setTile(position: Pair<Int, Int>, tile: Tile?) {
        data[position.first][position.second].setTile(tile)
    }

    override fun getTile(position: Pair<Int, Int>): Tile? {
        return data[position.first][position.second].getTile()
    }

    override fun addPiece(piece: Piece) {
        super.addPiece(piece)
        piece.tiles.forEach {
            setTile(it.getPosition(), it)
        }
    }

}

