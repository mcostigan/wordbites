package ui.cli

import game.Game
import game.piece.PieceChangeEvent
import observer.Observer
import ui.UserInterface

class CommandLineInterface(private var game: Game) : UserInterface, Observer<PieceChangeEvent> {
    private var moveParser = MoveParser()

    init {
        game.subscribeToPieceChanges(this)

    }

    override fun display() {
        print()
        while (true) {
            print("enter move")
            val move = moveParser.parseMove(readln())
            println(move)
            move(move.first, move.second)
        }
    }

    fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        game.move(from, to)
    }

    override fun notifyOfChange(data: PieceChangeEvent) {
        print()
    }

    fun print() {
        println()
        println("WORD BITES")
        println()
        game.getSquares().forEach { it ->
            println("--------------------------------")
            print("|")
            it.forEach {
                print(" ${it ?: " "} |")
            }
            println()
        }
        printScore()
    }

    fun printScore() {
        println("SCORE: ${game.getScore().getPoints()}")
    }

}