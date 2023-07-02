package ui.swing

import game.Game
import game.words.Word
import observer.Observer
import java.time.Instant
import java.util.*
import javax.swing.JLabel
import javax.swing.JPanel

class HeaderPanel(private var game: Game) : JPanel() {
    private var scoreLabel = ScoreLabel(game)
    private var timeLabel = TimeLabel(game)

    init {
        this.add(scoreLabel)
        this.add(timeLabel)
    }
}

class TimeLabel(game: Game) : JLabel() {
    private var gameEnd = Instant.now().plusMillis(game.getDuration().inWholeMilliseconds)

    private class RefreshTime(private var timeLabel: TimeLabel) : TimerTask() {
        override fun run() {
            timeLabel.draw()
        }
    }

    init {
        Timer().scheduleAtFixedRate(RefreshTime(this), 0, 1000)
    }

    fun draw() {
        val diff = (gameEnd.toEpochMilli() - Instant.now().toEpochMilli()) / 1000
        this.text = "Time: $diff"
    }
}

class ScoreLabel(private var game: Game) : JLabel("Score : ${game.getScore().getPoints()}"),
    Observer<Word> {
    init {
        game.subscribeToScore(this)
    }

    override fun notifyOfChange(data: Word) {
        this.text = "Score : ${game.getScore().getPoints().toString()}"
    }


}