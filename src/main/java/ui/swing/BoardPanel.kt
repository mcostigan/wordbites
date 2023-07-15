package ui.swing

import game.Game
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JPanel

class BoardPanel(private var game: Game) : JPanel() {
    private var piecePanels = mutableListOf<PiecePanel>()
    val squareSize = 80

    init {
        this.layout = null
        game.getPieces().forEach {
            val piece = PiecePanel(piece = it, squareSize)
            piecePanels.add(piece)
            piece.setBounds(squareSize)
            this.add(piece)
        }
        val boardListener = BoardMouseListener(game, this)
        this.addMouseListener(boardListener)
        this.addMouseMotionListener(boardListener)
    }

    fun getPiecePanelByPosition(x: Int, y: Int): PickedUpPiece? {
        val piece = this.piecePanels.firstOrNull {
            it.x <= x && it.x + it.width >= x && it.y <= y && it.y + it.height >= y
        } ?: return null
        return PickedUpPiece(piece, Pair(x - piece.x, y - piece.y))
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val d = game.getDimensions()
        val rows = d.first
        val cols = d.second
        for (row in 0..rows){
            val pos = row*squareSize
            g.drawLine(0, pos, this.squareSize*cols, pos)
        }

        for (col in 0..cols){
            val pos = col*squareSize
            g.drawLine(pos, 0, pos, this.squareSize*rows)
        }

    }

}

data class PickedUpPiece(val piece: PiecePanel, val offset: Pair<Int, Int>)
class BoardMouseListener(private var game: Game, private var boardUI: BoardPanel) : MouseListener, MouseMotionListener {
    private var dragService = DragService.getInstance(game, boardUI.squareSize)

    override fun mouseClicked(e: MouseEvent?) {}

    override fun mousePressed(e: MouseEvent?) {
        val piece = boardUI.getPiecePanelByPosition(e!!.x, e.y) ?: return
        dragService.pickUpPiece(piece, e.x, e.y)
    }

    override fun mouseReleased(e: MouseEvent?) {
        dragService.dropPiece(e!!.x, e.y)
    }

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

    override fun mouseDragged(e: MouseEvent?) {
        dragService.drag(e!!.x, e.y)
    }

    override fun mouseMoved(e: MouseEvent?) {}

}