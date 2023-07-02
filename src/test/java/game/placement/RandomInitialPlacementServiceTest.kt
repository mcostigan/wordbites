package game.placement

import game.board.Board
import game.board.BoardGrid
import game.piece.Piece
import game.piece.PieceFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.math.abs

internal class RandomInitialPlacementServiceTest {

    private val placementService = RandomInitialPlacementService()

    @Test
    fun `adds piece to board and sets new position`() {
        val mockBoard: Board = mock()
        val mockPiece: Piece = mock()
        whenever(mockPiece.getOffsets()).thenReturn(listOf(Pair(0, 0)))
        whenever(mockBoard.getDimensions()).thenReturn(Pair(8, 8))
        placementService.placePiece(mockPiece, mockBoard)
        verify(mockBoard).addPiece(eq(mockPiece))
        verify(mockPiece).initialPosition(any())
    }

    @Test
    fun `adds pieces spaced apart`() {
        val board = BoardGrid(8, 8)
        val pieces = PieceFactory().getPieces(6, 4)
        pieces.forEach {
            placementService.placePiece(it, board)
        }

        assert(board.getPieces().size == pieces.size)
        pieces.forEach { p1 ->
            pieces.forEach { p2 ->
                if (p1 != p2) {
                    // check that each tile has a manhattan distance of at least 2
                    p1.getPositions().forEach { t1 ->
                        p2.getPositions().forEach { t2 ->
                            val manhattanDistance = abs(t1.first - t2.first) + abs(t1.second - t2.second)
                            assert(manhattanDistance > 1)
                        }
                    }
                }
            }
        }
    }
}