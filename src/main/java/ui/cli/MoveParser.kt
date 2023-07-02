package ui.cli

class MoveParser {

    /**
     * Parses a string in (`row`, `col`) (`row`,`col`) format into pairs of integers, representing to origin and destination coordinates
     */
    fun parseMove(move: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return Pair( Pair(move[1].digitToInt(), move[3].digitToInt()), Pair(move[7].digitToInt(), move[9].digitToInt()))
    }
}