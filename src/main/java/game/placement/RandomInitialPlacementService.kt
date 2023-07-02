package game.placement

import game.board.Board
import game.piece.Piece
import java.util.*

class RandomInitialPlacementService : InitialPlacementService {
    private var random = Random()
    private var occupiedSpaces: MutableSet<Pair<Int, Int>> = mutableSetOf()

    override fun placePiece(piece: Piece, board: Board) {
        val dimensions = board.getDimensions()
        val height = dimensions.first
        val width = dimensions.second


        var position: Pair<Int, Int>
        do {
            position = Pair(random.nextInt(height - 1), random.nextInt(width - 1))
        } while (piece.getOffsets()
                .any { occupiedSpaces.contains(Pair(position.first + it.first, position.second + it.second)) }
        )

        piece.initialPosition(position)
        board.addPiece(piece)

        piece.getPositions().forEach { tilePosition ->
            occupiedSpaces.add(tilePosition)
            (-1..1).forEach { row ->
                (-1..1).forEach { col ->
                    if (row == 0 || col == 0) {

                        occupiedSpaces.add(Pair(tilePosition.first + row, tilePosition.second + col))
                    }
                }
            }

        }


    }

}