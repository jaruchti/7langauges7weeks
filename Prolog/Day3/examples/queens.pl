% Solution to the eight queens problem in Prolog.
% A position for a queen on the chess board is represented by a tuple (row, col).

% Rule for if a queen is in a valid position on the Chess board
valid_queen((Row, Col)) :-
  Range = [1,2,3,4,5,6,7,8],
  member(Row, Range), member(Col, Range).

% Rule for if all queens on the board are in valid positions.
valid_board([]).
valid_board([Head|Tail]) :- valid_queen(Head), valid_board(Tail).

% Rules to help use build variables with the rows, columns, and diagonals on the
% chess board.
rows([], []).
rows([(Row, _)|QueensTail], [Row|RowsTail]) :- 
  rows(QueensTail, RowsTail).

cols([], []).
cols([(_, Col)|QueensTail], [Col|ColsTail]) :-
  cols(QueensTail, ColsTail).

diags1([], []).
diags1([(Row, Col)|QueensTail], [Diagonal|DiagonalsTail]) :-
  Diagonal is Col - Row,
  diags1(QueensTail, DiagonalsTail).

diags2([], []).
diags2([(Row, Col)|QueensTail], [Diagonal|DiagonalsTail]) :-
  Diagonal is Col + Row,
  diags2(QueensTail, DiagonalsTail).

eight_queens(Board) :- 
  length(Board, 8),    % Eight queens on the board
  valid_board(Board),  % Validate queens are in valid positions

  % Build Rows, Cols, and Diagonals variables
  rows(Board, Rows),
  cols(Board, Cols),
  diags1(Board, Diags1),
  diags2(Board, Diags2),

  % Ensure queens not placed in same row/col/diagonal
  fd_all_different(Rows),
  fd_all_different(Cols),
  fd_all_different(Diags1),
  fd_all_different(Diags2).
