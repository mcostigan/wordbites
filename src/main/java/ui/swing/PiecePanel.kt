package ui.swing

import game.piece.Piece
import game.piece.PieceChangeEvent
import game.piece.PieceType
import observer.Observer
import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class PiecePanel(private var piece: Piece, private var squareSize: Int) : JPanel(), Observer<PieceChangeEvent> {
    init {
        this.isOpaque = true
        piece.subscribe(this)
        this.border = BorderFactory.createLineBorder(Color.BLACK)
        this.layout = when (piece.type) {
            PieceType.SINGLE -> GridLayout(1, 1)
            PieceType.HORIZONTAL -> GridLayout(1, 2)
            PieceType.VERTICAL -> GridLayout(2, 1)
        }

        piece.tiles.forEach {
            this.add(TilePanel(it))
        }
    }

    fun setBounds(squareSize: Int) {
        val dimensions = when (piece.type) {
            PieceType.SINGLE -> Pair(squareSize, squareSize)
            PieceType.HORIZONTAL -> Pair(squareSize * piece.tiles.size, squareSize)
            PieceType.VERTICAL -> Pair(squareSize, squareSize * piece.tiles.size)
        }
        this.setBounds(
            piece.getRootPosition().second * squareSize,
            piece.getRootPosition().first * squareSize,
            dimensions.first,
            dimensions.second
        )
    }

    fun setBounds(x: Int, y: Int) {
        this.setBounds(x, y, this.bounds.width, this.bounds.height)
    }

    fun getPosition(): Pair<Int, Int> = piece.getRootPosition()

    override fun notifyOfChange(data: PieceChangeEvent) {
        this.setBounds(squareSize)
    }

    private var state: PiecePanelState = DroppedPiecePanelState(this)
    fun setState(state: PiecePanelState) {
        this.state = state
        state.setColor()
    }

    fun pickup() = state.pickup()
    fun drag(x: Int, y: Int) = state.move(x, y)
    fun drop() = state.drop()
}

abstract class PiecePanelState(protected var context: PiecePanel) {
    abstract fun pickup()
    abstract fun move(x: Int, y: Int)
    abstract fun drop()
    abstract fun setColor()
}

class PickedUpPiecePanelState(context: PiecePanel) : PiecePanelState(context) {
    override fun pickup() {
        return
    }

    override fun move(x: Int, y: Int) {
        context.setBounds(x, y)
    }

    override fun drop() {
        context.setState(DroppedPiecePanelState(context))
    }

    override fun setColor() {
        context.background = Color.GREEN
    }

}

class DroppedPiecePanelState(context: PiecePanel) : PiecePanelState(context) {
    override fun pickup() {
        context.setState(PickedUpPiecePanelState(context))
    }

    override fun move(x: Int, y: Int) {
        return
    }

    override fun drop() {
        return
    }

    override fun setColor() {
        context.background = Color.WHITE
    }

}