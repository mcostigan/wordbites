# WordBites

This repo is a from-scratch clone of the [GamePigeon](https://en.wikipedia.org/wiki/GamePigeon) game of the same name. The player
drags pieces, made up of either one or two tiles, to positions on a board to create words (either left-to-right
top-to-bottom)

## Architecture

### Game

The `Game` provides an interface for interacting with `Piece`s, the `Board`, and the `Score`

### Board

The `Board` is a 2D grid which contains a collection of tiles. The board stores the locations of each piece and is
updated when moved

### Piece

A `Piece` is a collection of one or two `Tiles`. It has a root position.

### Tile

A `Tile` contains an offset, relative to the parent piece and a character. A tile can be moved, which results in the
movement of the parent `Piece`

### Scoring

When a piece is moved, the `WordsService` checks for any new words that may have been created from the move, which
relies on a `DictionaryService` to validate words. Finally, the `ScoringService` is called to determine how many points
a word earns

### UI

The `UserInterface` allows a user to interact with the game object. There are two implementations: a command line UI and
a Swing UI.

### Observer

An Observer pattern is used to communicate events across layers. For example, the `Game` object is notified when a piece
is moved so it can check for new words. Also, the UI level is notified of moves and score changes so the display can
update.
