package game.piece

import java.util.*

class PieceFactory {
    private var random = Random()
    private var randomLetterService = RandomLetterService()
    fun getPieces(singlePieces: Int, doublePieces: Int): Collection<Piece> {
        val pieces = mutableListOf<Piece>()
        for (i in 1..singlePieces) {
            pieces.add(getSinglePiece())
        }
        for (j in 1..doublePieces) {
            pieces.add(getDoublePiece())
        }
        return pieces
    }

    private fun getSinglePiece(): Piece = SinglePiece(randomLetterService.getRandomLetter())


    private fun getDoublePiece(): Piece {
        val letters = randomLetterService.getRandomPair()
        return if (random.nextDouble() > .5) {
            getVerticalPiece(letters.first, letters.second)
        } else {
            getHorizontalPiece(letters.first, letters.second)
        }
    }

    private fun getVerticalPiece(char1: Char, char2: Char): Piece = VerticalPiece(char1, char2)


    private fun getHorizontalPiece(char1: Char, char2: Char): Piece = HorizontalPiece(char1, char2)
}