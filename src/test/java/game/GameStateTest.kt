package game

import game.board.Board
import game.piece.Tile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.time.Duration
import kotlin.time.toKotlinDuration

internal class GameStateTest {
    private val mockGame: Game = mock()
    private val mockBoard: Board = mock()
    private val mockTile: Tile = mock()

    init {
        whenever(mockGame.getBoard()).thenReturn(mockBoard)
        whenever(mockBoard.getTile(any())).thenReturn(mockTile)

    }

    @Test
    fun `cannot move before game has started`() {
        val state = GameState.getInitialState(mockGame)
        assertThrows<UnsupportedOperationException> {
            state.move(Pair(0, 0), Pair(1, 1))
        }
    }

    @Test
    fun `playing an unstarted game starts the game`() {
        val state = GameState.getInitialState(mockGame)
        whenever(mockGame.getDuration()).thenReturn(Duration.ofSeconds(90).toKotlinDuration())
        state.play()
        verify(mockGame).setState(any())
    }

    @Test
    fun `cannot 'play' a live game`() {
        val state = GameState.getLiveState(mockGame)
        assertThrows<UnsupportedOperationException> {
            state.play()
        }
    }

    @Test
    fun `can move a live game`() {
        whenever(mockGame.getDuration()).thenReturn(Duration.ofSeconds(90).toKotlinDuration())
        val state = GameState.getLiveState(mockGame)
        state.move(Pair(0, 0), Pair(1, 1))
        verify(mockTile).move(any(), any())
    }

    @Test
    fun `if a live game has expired, cannot move and state is changed`() {
        whenever(mockGame.getDuration()).thenReturn(Duration.ofSeconds(0).toKotlinDuration())
        val state = GameState.getLiveState(mockGame)
        assertThrows<UnsupportedOperationException> {
            state.move(Pair(0, 0), Pair(1, 1))
        }
        verify(mockGame, atLeastOnce()).setState(any())
    }

    @Test
    fun `live state transitions to complete state after time out`() {
        whenever(mockGame.getDuration()).thenReturn(Duration.ofSeconds(0).toKotlinDuration())
        GameState.getLiveState(mockGame)
        verify(mockGame, atLeastOnce()).setState(any())
    }

    @Test
    fun `cannot 'play' or 'move' a completed game`() {
        val state = GameState.getCompleteState(mockGame)
        assertThrows<UnsupportedOperationException> {
            state.play()
        }
        assertThrows<UnsupportedOperationException> {
            state.move(Pair(0, 0), Pair(1, 1))
        }
    }
}