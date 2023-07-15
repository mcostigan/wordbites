package ui.swing

import game.piece.Tile
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

class TilePanel(private var tile: Tile) : JPanel() {
    init {
        this.add(JLabel(tile.getLetter().toString()))
        this.background = Color.GRAY
    }
}