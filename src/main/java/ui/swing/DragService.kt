package ui.swing

import game.Game


class DragService private constructor(private var game: Game, squareSize: Int) {
    private var from: Pair<Int, Int>? = null
    private var activePiece: PickedUpPiece? = null
    private var coordinateService = CoordinateService(squareSize)

    fun pickUpPiece(piece: PickedUpPiece, x: Int, y: Int) {
        from = coordinateService.getSquare(x, y)
        activePiece = piece
        activePiece!!.piece.pickup()
    }

    fun drag(x: Int, y: Int) {
        activePiece?.piece?.drag(x - activePiece!!.offset.first, y - activePiece!!.offset.second)
    }

    fun dropPiece(x: Int, y: Int) {
        val to = coordinateService.getSquare(x, y)

        game.move(from!!, to)
        activePiece!!.piece.drop()
        activePiece = null
        from = null
    }

    companion object {
        private var instance: DragService? = null
        fun getInstance(game: Game, squareSize: Int): DragService {
            if (instance == null) {
                instance = DragService(game, squareSize)
            }
            return instance!!
        }
    }
}

class CoordinateService(private val squareSize: Int) {
    fun getCoordinates(square: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(square.first * squareSize, square.second * squareSize)
    }

    fun getSquare(x: Int, y: Int): Pair<Int, Int> {

        return Pair(y / squareSize, x / squareSize)
    }
}