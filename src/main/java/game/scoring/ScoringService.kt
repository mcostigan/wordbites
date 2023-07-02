package game.scoring

interface ScoringService {

    fun scoreWord(word: String): Int

}

class ScoringServiceFactory {
        fun get(): ScoringService = DefaultScoringService()
        fun get(type: String): ScoringService = DefaultScoringService()
}

class DefaultScoringService : ScoringService {
    override fun scoreWord(word: String): Int {
        return 100 + (word.length - 3) * 100
    }

}