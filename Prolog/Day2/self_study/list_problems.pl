% Creation of Prolog rules to reverse the elements of a list, find the smallest element in a list,
% and sort the elements of a list.

my_reverse(L, R) :- reverseHelper(L, [], R).

% Helper function for reversing a list.  The second variable
% is an "accumular" used to build the reversed list.
reverseHelper([], List, List).    % Base case, no more elements to reverse
reverseHelper([Head|Tail], Accum, Reverse) :-
  reverseHelper(Tail, [Head | Accum], Reverse). % Recursive case, add the head of the list to the accumulator

list_min([Min], Min). % Min of a single element list is the element itself
list_min([F,S|T], Min) :- F < S,  list_min([F|T], Min). % Discard second element when first is smaller
list_min([F,S|T], Min) :- F >= S, list_min([S|T], Min). % Discard first element when second is smaller 

% Quicksort implementation

% Empty list is sorted by default
qsort([], []).                 

% List is sorted where
% 1) Smaller is a list containing elements <= Pivot and Larger is a list containing elements >= Pivot
% 2) Smaller is sorted
% 3) Larger is sorted
% 4) Sorted is the result of appending Smaller ++ Pivot ++ Larger
qsort([Pivot|Tail], Sorted) :-
  partition(Pivot, Tail, Smaller, Larger),            % 1
  qsort(Smaller, SmallerSorted),                      % 2
  qsort(Larger,  LargerSorted),                       % 3
  append(SmallerSorted, [Pivot|LargerSorted], Sorted).% 4

% Empty lists are partitioned by default
partition(_Pivot, [], [], []).

% List is partitioned when Head =< Pivot and Smaller contains Head 
% AND when Head > Pivot and Larger contins Head.
partition(Pivot, [Head|Tail], [Head|Smaller], Larger) :-
  Head =< Pivot, partition(Pivot, Tail, Smaller, Larger).
partition(Pivot, [Head|Tail], Smaller, [Head|Larger]) :-
  Head >  Pivot, partition(Pivot, Tail, Smaller, Larger).
