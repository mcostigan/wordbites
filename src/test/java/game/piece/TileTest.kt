package game.piece

import game.board.Board
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

internal class TileTest {

    @Test
    fun `moving a tile moves parent piece`() {
        val piece: Piece = mock()
        val board: Board = mock()

        val tile = Tile(Pair(0, 0), 'a', piece)

        tile.move(Pair(1, 0), board)
        verify(piece).setPosition(any(), any())
    }
}