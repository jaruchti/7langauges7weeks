% Playground to test unification (finding solutions to symbolic logic) with the interpreter
% e.g. twin_cats(One, Two).
% One = lion
% Two = lion ? a
% One = lion
% Two = tiger
% One = tiger
% Two = lion
% One = tiger
% Two = tiger

cat(lion).
cat(tiger).

dorothy(X, Y, Z) :- X = lion, Y = tiger, Z = bear.
twin_cats(X, Y) :- cat(X), cat(Y).
