package game.words

import game.board.Board
import game.board.Square

interface WordsService {
    fun buildWordsIncludingPosition(square: Square, board: Board): Collection<Word>
}

class WordsServiceFactory {
    fun get(dictionaryService: DictionaryService): WordsService = DefaultWordsService(dictionaryService)
    fun get(type: String, dictionaryService: DictionaryService) = get(dictionaryService)
}

class DefaultWordsService(
    private var dictionaryService: DictionaryService
) : WordsService {

    override fun buildWordsIncludingPosition(square: Square, board: Board): Collection<Word> {
        val left = buildWord(board, square.getPosition(), Pair(0, -1))
        val right = buildWord(board, square.getPosition(), Pair(0, 1))
        val up = buildWord(board, square.getPosition(), Pair(-1, 0))
        val down = buildWord(board, square.getPosition(), Pair(1, 0))

        val candidateWords: Collection<Collection<Char>> = if (square.getLetter() == null) {
            listOf(left, right, up, down)
        } else {
            listOf(
                left.plus(square.getLetter()!!).plus(right),
                up.plus(square.getLetter()!!).plus(down)
            )
        }
        return candidateWords.filter { it.size > 2 }.map { it.joinToString(separator = "") }
            .filter { dictionaryService.isWord(it) }.map { Word(it) }

    }


    private fun buildWord(
        board: Board,
        startPosition: Pair<Int, Int>,
        delta: Pair<Int, Int>
    ): Collection<Char> {
        val result = mutableListOf<Char>()
        var position = startPosition
        do {
            position = Pair(position.first + delta.first, position.second + delta.second)
            val letter = board.getLetter(position)

            if (letter != null) {
                result.add(letter)
            }

        } while (letter != null)

        if (delta.first < 0 || delta.second < 0) {
            result.reverse()
        }

        return result
    }


}


