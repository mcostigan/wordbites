package game.scoring

import game.words.Word
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import observer.Observable


internal class ScoreTest {
    private var mockObservable = mock<Observable<Word>>()
    private var mockScoringService = mock<ScoringService>()

    private fun createScore(): Score {
        val score = Score(mockScoringService, mockObservable)
        score.addWord(Word("test"))
        return score
    }

    @Test
    fun `adding a new words increases points and notifies subscribers`() {
        val score = createScore()
        val newWord = Word("newWord")
        val oldPoints = score.getPoints()

        whenever(mockScoringService.scoreWord(any())).thenReturn(100)
        score.addWord(newWord)

        assert(score.getPoints() - oldPoints == 100)
        verify(mockObservable).publish(eq(newWord))
    }

    @Test
    fun `adding a repeat word does not increase points and does not notifu subscribers`() {
        whenever(mockScoringService.scoreWord(any())).thenReturn(100)
        val score = createScore()
        val newWord = Word("test")
        val oldPoints = score.getPoints()

        score.addWord(newWord)

        assert(score.getPoints() == oldPoints)
        verify(mockObservable, never()).publish(newWord)
    }
}