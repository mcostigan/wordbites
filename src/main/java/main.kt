import game.GameBuilder
import ui.swing.SwingUserInterface

fun main() {
    val game = GameBuilder()
        .withBoardSize(8)
        .withSinglePieces(6)
        .withDoublePieces(4)
        .withDuration(90)
        .build()
    val ui = SwingUserInterface(game)
    ui.display()

}