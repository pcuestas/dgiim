"""Illustration of tournament.

Authors:
    Alejandro Bellogin <alejandro.bellogin@uam.es>

Modified by
    Pablo Cuesta Sierra
    Álvaro Zamanillo Sáez
"""

from __future__ import annotations  # For Python 3.7

import sys
import copy
import numpy as np
#from g2351_p2_05_cuesta_zamanillo import Solution1, Solution2, Solution3

from game import Player, TwoPlayerGameState, TwoPlayerMatch
from heuristic import simple_evaluation_function
from heuristic import Heuristic
from strategy import MinimaxAlphaBetaStrategy
from reversi import (
    Reversi,
    from_array_to_dictionary_board,
    from_dictionary_to_array_board,
)
from tournament import StudentHeuristic, Tournament
from random_boards import board08
from test_heuristics import *



def create_match(player1: Player, player2: Player) -> TwoPlayerMatch:

    initial_board = None#np.zeros((dim_board, dim_board))
    initial_player = player1

    #initial_board = ([
    #    '........',
    #    '........',
    #    '........',
    #    '...BW...',
    #    '...WB...',
    #    '........',
    #    '........',
    #    '........'
    #])

    boards = board08 # board to select for the match
    
    #random board:
    initial_board = boards[np.random.randint(0,len(boards))]

    if initial_board is None:
        height, width = 8, 8
    else:
        height = len(initial_board)
        width = len(initial_board[0])
        try:
            initial_board = from_array_to_dictionary_board(initial_board)
        except ValueError:
            raise ValueError('Wrong configuration of the board')
        else:
            print("Successfully initialised board from array")

    game = Reversi(
        player1=player1,
        player2=player2,
        height=height,
        width=width
    )

    game_state = TwoPlayerGameState(
        game=game,
        board=initial_board,
        initial_player=initial_player,
    )
#max_seconds_per_move=1000 before
    return TwoPlayerMatch(game_state, max_seconds_per_move=10, gui=False)

tour = Tournament(max_depth=3, init_match=create_match)


if len(sys.argv)==3: # to take the heuristics as arguments
    strats = {
          'opt1': [eval(sys.argv[1])],
          'opt2':[eval(sys.argv[2])]
        }
else:
    strats = {
            'opt1': [Solution1],
            'opt2': [Solution2],
            #'opt7': [HeuristicModify]
            #'opt00': [HeuristicOnlyWeight1], 
            #'opt01':[HeuristicOnlyWeight2], 
            #'opt1': [Heuristic1], 
            #'opt2': [Heuristic2],
            #'opt4': [HeuristicCaptureCorner],
            #'opt5': [HeuristicMobility], 
            #'opt8':[HeuristicDontGiveCorner]
            'opt3':[Solution3]
    }

n = 1
scores, totals, names = tour.run(
    student_strategies=strats,
    increasing_depth=False,
    n_pairs=n,
    allow_selfmatch=False,
)

print(
    'Results for tournament where each game is repeated '
    + '%d=%dx2 times, alternating colors for each player' % (2 * n, n),
)

# print(totals)
# print(scores)

print('\ttotal:', end='')
#for name1 in names:
    #print('\t%s' % (name1), end='')
print()
for name1 in names:
    print('%s\t%d' % (name1, totals[name1]), end='')
    for name2 in names:
        if name1 == name2:
            print('\t---', end='')
        else:
            print('\t%d' % (scores[name1][name2]), end='')
    print()
