package game.placement

import game.board.Board
import game.piece.Piece

interface InitialPlacementService {
    fun placePiece(piece: Piece, board: Board)
}

class PlacementServiceFactory {
    fun get(): InitialPlacementService = RandomInitialPlacementService()
    fun get(type: String): InitialPlacementService = get()
}

