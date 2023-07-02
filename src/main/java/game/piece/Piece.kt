package game.piece

import game.board.Board
import observer.Observable
import observer.Observer


enum class PieceType {
    SINGLE,
    HORIZONTAL,
    VERTICAL
}

abstract class Piece(private var observable: Observable<PieceChangeEvent>) {

    private lateinit var position: Pair<Int, Int>
    internal lateinit var tiles: Collection<Tile>
    fun getRootPosition(): Pair<Int, Int> = position
    fun getPositions(): Collection<Pair<Int, Int>> =
        tiles.map { Pair(position.first + it.getOffset().first, position.second + it.getOffset().second) }

    fun getOffsets() = tiles.map { it.getOffset() }

    fun initialPosition(position: Pair<Int, Int>) {
        this.position = position
    }

    fun setPosition(position: Pair<Int, Int>, board: Board) {
        val changeEvent = PieceChangeEvent(getRootPosition(), position, tiles.map { it.getOffset() })

        tiles.forEach {
            board.setTile(it.getPosition(), null)
        }

        this.position = position
        tiles.forEach {
            board.setTile(it.getPosition(), it)
        }
        observable.publish(changeEvent)
    }

    fun subscribe(observer: Observer<PieceChangeEvent>) {
        this.observable.subscribe(observer)
    }

    abstract val type: PieceType
}

class SinglePiece(char: Char, observable: Observable<PieceChangeEvent> = Observable()) : Piece(observable) {
    init {
        super.tiles = listOf(Tile(Pair(0, 0), char, this))
    }

    override val type: PieceType = PieceType.SINGLE
}

class HorizontalPiece(char1: Char, char2: Char, observable: Observable<PieceChangeEvent> = Observable()) :
    Piece(observable) {
    init {
        super.tiles = listOf(Tile(Pair(0, 0), char1, this), Tile(Pair(0, 1), char2, this))
    }

    override val type: PieceType = PieceType.HORIZONTAL

}

class VerticalPiece(char1: Char, char2: Char, observable: Observable<PieceChangeEvent> = Observable()) : Piece(observable) {
    init {
        super.tiles = listOf(Tile(Pair(0, 0), char1, this), Tile(Pair(1, 0), char2, this))
    }

    override val type: PieceType = PieceType.VERTICAL

}



