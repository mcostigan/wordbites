package game

import game.board.Board
import game.piece.Piece
import game.piece.PieceChangeEvent
import game.placement.InitialPlacementService
import game.scoring.Score
import game.scoring.ScoringService
import game.words.Word
import game.words.WordsService
import observer.Observer
import java.time.Duration
import kotlin.time.toKotlinDuration

class Game internal constructor(
    private var board: Board,
    private var duration: Duration,
    scoringService: ScoringService,
    private var wordsService: WordsService,
    private var placementService: InitialPlacementService
) : Observer<PieceChangeEvent> {

    private var score = Score(scoringService)
    private var pieces: MutableCollection<Piece> = mutableListOf()
    private var state: GameState = GameState.getInitialState(this)


    fun play() = state.play()

    fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        state.move(from, to)
    }

    fun getBoard() = board

    fun getSquares(): List<List<Char?>> {
        return (0..7).map { row ->
            (0..7).map { col ->
                board.getLetter(Pair(row, col))
            }
        }
    }


    fun subscribeToPieceChanges(observer: Observer<PieceChangeEvent>) {
        board.getPieces().forEach {
            it.subscribe(observer)
        }
    }

    fun getPieces() = board.getPieces()

    fun subscribeToScore(observer: Observer<Word>) {
        score.subscribe(observer)
    }

    fun getDimensions() = board.getDimensions()

    fun addPiece(piece: Piece) {
        placementService.placePiece(piece, board)
        pieces.add(piece)
        piece.subscribe(this)
    }

    override fun notifyOfChange(data: PieceChangeEvent) {
        data.offsets.forEach { it ->
            val oldSquare =
                board.getSquare(Pair(data.oldPosition.first + it.first, data.oldPosition.second + it.second))
            val newSquare =
                board.getSquare(Pair(data.newPosition.first + it.first, data.newPosition.second + it.second))
            var words = wordsService.buildWordsIncludingPosition(oldSquare, board)
            words.forEach { word -> score.addWord(word) }
            words = wordsService.buildWordsIncludingPosition(newSquare, board)
            words.forEach { word -> score.addWord(word) }
        }

    }

    fun getScore() = score

    fun getDuration() = this.duration.toKotlinDuration()

    internal fun setState(state: GameState) {
        this.state = state
    }

}

