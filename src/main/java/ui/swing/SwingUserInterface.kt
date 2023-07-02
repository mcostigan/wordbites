package ui.swing

import game.Game
import game.words.Word
import ui.UserInterface
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import java.util.Timer
import javax.swing.*

class SwingUserInterface(private var game: Game) : UserInterface {
    private var gameUI = GameUI(game)


    override fun display() {
        gameUI.display()
    }
}

class GameUI(var game: Game) : JFrame() {
    private var state: GameUIState = PendingUIState(this)

    init {
        this.setSize(1000, 1000)
        this.defaultCloseOperation = EXIT_ON_CLOSE
    }

    fun display() {
        state.build()
        this.isVisible = true
    }


    fun setState(state: GameUIState) {
        this.state.breakDown()
        this.state = state
        state.build()
        this.revalidate()
    }
}

abstract class GameUIState(protected var context: GameUI) : JPanel() {
    abstract fun build()
    fun breakDown() {
        context.remove(this)
    }
}

class PendingUIState(context: GameUI) : GameUIState(context) {
    private var startButton = JButton("Start")

    private class PlayGameActionListener(private var context: GameUI) : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            context.setState(LiveUIState(context))
            context.game.play()
        }

    }

    init {
        startButton.addActionListener(PlayGameActionListener(context))

    }

    override fun build() {
        this.layout = BorderLayout()
        this.add(startButton, BorderLayout.PAGE_START)
        context.add(this)
    }

}

class LiveUIState(context: GameUI) : GameUIState(context) {
    private class CompletionTimerTask(private var game: GameUI) : TimerTask() {
        override fun run() {
            game.setState(CompleteUIState(game))
        }
    }

    override fun build() {
        Timer().schedule(CompletionTimerTask(context), context.game.getDuration().inWholeMilliseconds)
        this.layout = BorderLayout()
        this.add(HeaderPanel(context.game), BorderLayout.PAGE_START)
        this.add(BoardPanel(context.game), BorderLayout.CENTER)
        context.add(this)
    }

}

class CompleteUIState(context: GameUI) : GameUIState(context) {

    override fun build() {
        this.layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        this.add(ScoreLabel(context.game))
        context.game.getScore().getWords().forEach {
            this.add(ScoredWord(it))
        }
        context.add(this)
    }
}

class ScoredWord(private var word: Word) : JPanel() {
    init {
        this.add(JLabel(word.word))
        this.add(JLabel(word.points.toString()))
    }
}