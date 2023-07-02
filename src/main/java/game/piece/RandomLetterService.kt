package game.piece

class RandomLetterService {
    private var chars = ('a'..'z')
    private var pairs = chars.flatMap { first ->
        chars.map { second -> Pair(first, second) }
    }

    fun getRandomLetter(): Char {
        return chars.random()
    }

    fun getRandomPair(): Pair<Char, Char> {
        return pairs.random()
    }
}