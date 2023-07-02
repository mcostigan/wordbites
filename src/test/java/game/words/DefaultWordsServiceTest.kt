package game.words

import game.board.Board
import game.board.Square
import org.junit.Test
import org.mockito.kotlin.*

internal class DefaultWordsServiceTest {
    private var dictionaryService = mock<DictionaryService>()
    var wordService = DefaultWordsService(dictionaryService)


    private fun mockBoard(letters: Collection<Pair<Pair<Int, Int>, Char>>): Board {
        val board = mock<Board>()
        val mockedSquares = letters.map { it.first }

        whenever(board.getLetter(any())).thenReturn(null)
        letters.forEach {
            whenever(board.getLetter(it.first)).thenReturn(it.second)
        }

        return board
    }

    /**
     *
    - - - a - - -
    - - - n - - -
    - - - d - - -
    b a n - c a q
    - - - p - - -
    - - - a - - -
    - - - t - - -
     */

    @Test
    fun `removing a letter from a square can create up to 4 new words`() {
        val letters = listOf(
            Pair(Pair(0, 3), 'a'),
            Pair(Pair(1, 3), 'n'),
            Pair(Pair(2, 3), 'd'),
            Pair(Pair(3, 0), 'b'),
            Pair(Pair(3, 1), 'a'),
            Pair(Pair(3, 2), 'n'),
            Pair(Pair(3, 4), 'c'),
            Pair(Pair(3, 5), 'a'),
            Pair(Pair(3, 6), 'q'),
            Pair(Pair(4, 3), 'p'),
            Pair(Pair(5, 3), 'a'),
            Pair(Pair(6, 3), 't'),
        )
        val board = mockBoard(letters = letters)
        whenever(dictionaryService.isWord(any())).thenReturn(true)
        whenever(dictionaryService.isWord(eq("caq"))).thenReturn(false)


        val square = mock<Square>()
        whenever(square.getPosition()).thenReturn(Pair(3, 3))
        whenever(square.getLetter()).thenReturn(null)
        val newWords = wordService.buildWordsIncludingPosition(square, board)

        assert(newWords.size == 3)
        verify(dictionaryService, times(4)).isWord(any())
        assert(!newWords.any { it.word == "caq" })
    }

    /**
     *
    - - - h
    - - - a
    - - - c
    b a c k s

     */
    @Test
    fun `adding a letter to a square can create up to 2 new words`() {
        val letters = listOf(
            Pair(Pair(0, 3), 'h'),
            Pair(Pair(1, 3), 'a'),
            Pair(Pair(2, 3), 'c'),
            Pair(Pair(3, 0), 'b'),
            Pair(Pair(3, 1), 'a'),
            Pair(Pair(3, 2), 'c'),
            Pair(Pair(3, 3), 'k'),
            Pair(Pair(3, 4), 's'),
        )
        val board = mockBoard(letters = letters)
        whenever(dictionaryService.isWord(any())).thenReturn(true)


        val square = mock<Square>()
        whenever(square.getPosition()).thenReturn(Pair(3, 3))
        whenever(square.getLetter()).thenReturn('k')
        val newWords = wordService.buildWordsIncludingPosition(square, board)

        assert(newWords.size == 2)
        verify(dictionaryService).isWord(eq("backs"))
        verify(dictionaryService).isWord(eq("hack"))
    }

}