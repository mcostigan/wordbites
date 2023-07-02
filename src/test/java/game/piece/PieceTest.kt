package game.piece

import game.board.Board
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import observer.Observable

internal class PieceTest {

    @Mock
    var board: Board = mock()

    @Mock
    var observable = mock<Observable<PieceChangeEvent>>()

    @Test
    fun `setting position of piece changes squares for each tile and publishes change event`() {
        val piece = HorizontalPiece('a', 'b', observable)
        piece.initialPosition(Pair(0, 0))

        piece.setPosition(Pair(1, 0), board)
        verify(board, times(2)).setTile(any(), ArgumentMatchers.eq(null))
        verify(board, times(2)).setTile(any(), ArgumentMatchers.notNull())
        verify(observable).publish(any())

    }
}