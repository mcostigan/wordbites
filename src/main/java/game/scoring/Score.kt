package game.scoring

import game.words.Word
import observer.Observable
import observer.Observer

class Score(
    private var scoringService: ScoringService,
    private var observable: Observable<Word> = Observable()
) {
    private var score = 0
    private var words: MutableSet<String> = mutableSetOf()
    private var scoredWords: MutableCollection<Word> = mutableListOf()

    fun addWord(word: Word) {
        if (words.contains(word.word)) {
            return
        }
        word.points = scoringService.scoreWord(word.word)
        score += word.points
        words.add(word.word)
        scoredWords.add(word)
        observable.publish(word)
    }

    fun getWords() = scoredWords.sortedBy { -it.points }
    fun getPoints() = score
    fun subscribe(observer: Observer<Word>) {
        observable.subscribe(observer)
    }
}