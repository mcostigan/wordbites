package game

import java.time.Instant
import java.util.*

abstract class GameState(protected var context: Game) {
    abstract fun move(from: Pair<Int, Int>, to: Pair<Int, Int>)
    abstract fun play()

    companion object {
        fun getInitialState(game: Game): GameState = SetUpState(game)
        fun getLiveState(game: Game): GameState = LiveState(game, Instant.now())
        fun getCompleteState(game: Game): GameState = CompleteState(game)
    }
}

private class SetUpState(context: Game) : GameState(context) {
    override fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        throw UnsupportedOperationException("Attempted to move before game started")
    }

    override fun play() {
        context.setState(LiveState(context, Instant.now()))
    }
}

private class LiveState(context: Game, start: Instant) : GameState(context) {
    private var gameEnd: Instant = start.plusSeconds(context.getDuration().inWholeSeconds)

    private class SetStateTask(private var game: Game) : TimerTask() {
        override fun run() {
            game.setState(CompleteState(game))
        }
    }

    init {
        Timer().schedule(SetStateTask(context), context.getDuration().inWholeMilliseconds)
    }

    override fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        if (!isLive()) {
            context.setState(CompleteState(context))
            throw UnsupportedOperationException("Attempted to move after game completion")
        }

        val board = context.getBoard()
        val fromTile = board.getTile(from) ?: return
        fromTile.move(to, context.getBoard())

    }

    override fun play() {
        throw UnsupportedOperationException("Game already started")
    }

    private fun isLive(): Boolean = Instant.now().isBefore(gameEnd)

}

private class CompleteState(context: Game) : GameState(context) {
    override fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        throw UnsupportedOperationException("Game ended")
    }

    override fun play() {
        throw UnsupportedOperationException("Game ended")
    }
}