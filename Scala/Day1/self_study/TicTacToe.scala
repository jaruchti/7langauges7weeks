// Exercise: Write a game that will take a tic-tac-toe board with X, O, and blank
// characters and detect the winner or whether there is a tie or no winner yet.  
// Use classes where appropriate.

// Utilize case objects to represent
// markers on the Tic-Tac-Toe board
sealed abstract class Marker
case object X extends Marker
case object O extends Marker
case object B extends Marker // Blank tile

// Representation of the Tic-Tac-Toe board.
// Initialize with a 2-D array of markers
// representing the current state of play.

class TicTacToeBoard( board : Array[Array[Marker]] ){
  val BoardDimensions = 3 // Tic-Tac-Toe is 3x3 Matrix
                          // @todo: Handle exceptional cases where an invalid board 
                          // (non 3x3 matrix) is passed to constructor
                           
  // Returns true if the game is over (no blank spaces in the board matrix).
  def gameOver() = {
    !board.flatten.contains(B)
  }

  // Returns a string representation of the Tic-Tac-Toe
  // game winner:
  //   "X" for an X win
  //   "O" for an O win
  //   "Tie" for a draw
  //   "Incomplete" if the game is not yet finished (blank tiles remain)
  def getWinner() : String = {
    // Test that the game has been completed (e.g. no blank entries)
    if ( !gameOver() )           { return "Incomplete" }

    // Test each row/column to determine if three markers in a row can be found
    for (row <- board )          { if (row.distinct.size == 1) return row(0).toString() }
    for (col <- board.transpose) { if (col.distinct.size == 1) return col(0).toString() }

    // Test the two diagonals to determine if three markers in a row can be found
    var diag1 = for (i <- 0 until BoardDimensions)  yield board(i)(i) 
    var diag2 = for (i <- 0 until BoardDimensions)  yield board(i)(BoardDimensions - i - 1)
    if (diag1.distinct.size == 1) return diag1(0).toString()
    if (diag2.distinct.size == 1) return diag2(0).toString()

    return "Tie"
  }
}

// Define a few test cases to instrument winning conditions.
// @todo: Investigate automatted unit testing frameworks in 
//        Scala to automate these tests.
val xWinBoard       = new TicTacToeBoard( Array( Array( X, X, X),
                                                 Array( X, O, O),
                                                 Array( O, O, X) ) )
val oWinBoard       = new TicTacToeBoard( Array( Array( X, X, O),
                                                 Array( X, O, O),
                                                 Array( O, X, X) ) )
val incompleteBoard = new TicTacToeBoard( Array( Array( X, O, X),
                                                 Array( X, B, O),
                                                 Array( O, X, X) ) )
val tieBoard        = new TicTacToeBoard( Array( Array( X, O, X),
                                                 Array( X, O, O),
                                                 Array( O, X, X) ) )

println( "Winner: " + xWinBoard.getWinner() ); // <- X
println( "Winner: " + oWinBoard.getWinner() ); // <- O
println( "Winner: " + tieBoard.getWinner() );  // <- Tie
println( "Winner: " + incompleteBoard.getWinner() ); // <- Incomplete
