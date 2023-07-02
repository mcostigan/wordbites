package game

import game.board.BoardGrid
import game.piece.PieceFactory
import game.placement.InitialPlacementService
import game.placement.PlacementServiceFactory
import game.scoring.ScoringService
import game.scoring.ScoringServiceFactory
import game.words.DictionaryServiceFactory
import game.words.WordsService
import game.words.WordsServiceFactory
import java.time.Duration

class GameBuilder {
    private var scoringServiceFactory = ScoringServiceFactory()
    private var wordsServiceFactory = WordsServiceFactory()
    private var placementServiceFactory = PlacementServiceFactory()
    private var dictionaryServiceFactory = DictionaryServiceFactory.getInstance()
    private var pieceFactory = PieceFactory()

    // build variables
    private var dictionaryService = dictionaryServiceFactory.getDictionaryService()
    private var scoringService: ScoringService = scoringServiceFactory.get()
    private var wordsService: WordsService = wordsServiceFactory.get(dictionaryService)
    private var placementService: InitialPlacementService = placementServiceFactory.get()
    private var boardSize = 8
    private var singlePieces = 6
    private var doublePieces = 4
    private var gameDuration: Duration = Duration.ofSeconds(90)

    fun withPlacementService(serviceName: String): GameBuilder {
        this.placementService = placementServiceFactory.get(serviceName)
        return this
    }

    fun withScoringService(scoringServiceName: String): GameBuilder {
        this.scoringService = scoringServiceFactory.get(scoringServiceName)
        return this
    }

    fun withWordsService(serviceName: String): GameBuilder {
        this.wordsService = wordsServiceFactory.get(serviceName, dictionaryService)
        return this
    }

    fun withDictionaryService(serviceName: String): GameBuilder {
        this.dictionaryService = dictionaryServiceFactory.getDictionaryService(serviceName)
        this.wordsService = wordsServiceFactory.get(serviceName, dictionaryService)
        return this
    }

    fun withBoardSize(size: Int): GameBuilder {
        this.boardSize = size
        return this
    }

    fun withSinglePieces(count: Int): GameBuilder {
        this.singlePieces = count
        return this
    }

    fun withDoublePieces(count: Int): GameBuilder {
        this.doublePieces = count
        return this
    }

    fun withDuration(seconds: Long): GameBuilder {
        this.gameDuration = Duration.ofSeconds(seconds)
        return this
    }

    fun build(): Game {
        val squares = BoardGrid(boardSize, boardSize)
        val pieces = pieceFactory.getPieces(singlePieces, doublePieces)

        val game = Game(squares, gameDuration, scoringService, wordsService, placementService)
        pieces.forEach {
            game.addPiece(it)
        }
        return game
    }
}