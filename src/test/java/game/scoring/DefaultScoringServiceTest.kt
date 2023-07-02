package game.scoring

import org.junit.jupiter.api.Test

internal class DefaultScoringServiceTest {
    var scoringService = DefaultScoringService()

    @Test
    fun scoreWord() {
        assert(scoringService.scoreWord("tes") == 100)
        assert(scoringService.scoreWord("test") == 200)
    }
}