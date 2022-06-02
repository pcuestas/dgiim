"""
Script to time the minimax and alpha-beta algorithms using a reversi match. 

Authors:
    Pablo Cuesta Sierra
    Álvaro Zamanillo Sáez
"""

from __future__ import annotations  # For Python 3.7

from game import (Player, TwoPlayerGameState, TwoPlayerMatch)
from tournament import StudentHeuristic
from reversi import Reversi, from_array_to_dictionary_board, from_dictionary_to_array_board
from heuristic import Heuristic
from strategy import (MinimaxAlphaBetaStrategy, MinimaxStrategy)
import timeit
import sys

class tournament2_Solution3(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_solution3"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
  def utility(self, state: TwoPlayerGameState) -> float:
    return  0.2 * self.coin_diff(state)                    \
          + 0.4 * self.corner_diff(state)                  \
          + 0.4 * self.frontiers(state)

  def coin_diff(self, state: TwoPlayerGameState) -> float:
    """Difference in the number of coins."""
    total = len(state.board)
    if total:
      return 100 *                                                            \
            (sum(x == state.player1.label for x in state.board.values())      \
            -sum(x == state.player2.label for x in state.board.values())) / total
    else:
      return 0
      
  def static_weights_player(self, board: dict, player_label: Any, weights) -> float:
    return sum(
            weights[key[0] - 1][key[1] - 1]
            for key in board.keys() if (board[key] == player_label)
        )

  def static_weights_heuristic(self, state: TwoPlayerGameState) -> float:
    ''' 100 * (sum of p1's weights - sum of p2's weights) / (# of coins in the board) '''
    weights = [
      [100,-10, 11,  6,  6, 11,-10,100],
      [-10,-20,  1,  2,  2,  1,-20,-10],
      [ 10,  1,  5,  4,  4,  5,  1, 10],
      [  6,  2,  4,  2,  2,  4,  2,  6],
      [  6,  2,  4,  2,  2,  4,  2,  6],
      [ 10,  1,  5,  4,  4,  5,  1, 10],
      [-10,-20,  1,  2,  2,  1,-20,-10],
      [100,-10, 11,  6,  6, 11,-10,100],
    ]
    return 100 *                                                                                    \
            (self.static_weights_player(state.board, state.player1.label, weights)                       \
          - self.static_weights_player(state.board, state.player2.label, weights)) / len(state.board) 

  def corner_diff(self, state: TwoPlayerGameState) -> float:
    """Difference in the number of corners captured."""
    corner = [state.board.get((1, 1)), state.board.get((1, state.game.height)), state.board.get((state.game.width, 1)),
              state.board.get((state.game.width, state.game.height))]
    p1_corner = corner.count(state.player1.label)
    p2_corner = corner.count(state.player2.label)
    if (p1_corner + p2_corner):
      return 100 * (p1_corner - p2_corner) / (p1_corner + p2_corner)
    else:
      return 0
  ####################################################################################
  ##### FRONTIERS ####################################################################

  def unoccupied(self, i,j,board) -> bool:
    '''whether position i,j of the board array is occupied, False if it is out of bounds'''
    if (i < 0) or (j < 0) or (i >= 8) or (j >= 8):
      return False 
    return board[i][j] == '.'

  def is_frontier(self, i,j, board) -> bool:
    if (i,j) in [(0,0),(0,7),(7,0),(7,7)]: 
      return False
    surrounding = [
                (i-1,j-1),(i  ,j-1),(i+1,j-1),
                (i-1,j  ),          (i+1,j  ),
                (i-1,j+1),(i  ,j+1),(i+1,j+1)
        ]
    return any(self.unoccupied(i,j,board) for (i,j) in surrounding)

  def frontiers_player(self, state: TwoPlayerGameState, label: Any) -> float:
    board_arr = from_dictionary_to_array_board(state.board, 8, 8)
    arr_pieces = [(j-1,i-1) for (i,j) in state.board.keys() if state.board[(i,j)] == label]
    return sum(self.is_frontier(i,j, board_arr) for (i,j) in arr_pieces)

  def frontiers(self, state: TwoPlayerGameState) -> float:
    p1,p2 = self.frontiers_player(state, state.player1.label), self.frontiers_player(state, state.player2.label)
    if (p1+p2):
      return 100*(p2-p1)/(p1+p2)
    else:
      return 0

sol3_heuristic = tournament2_Solution3()

def constant_evaluation_function(state: TwoPlayerGameState) -> float:
    return 123

#eval_funct = constant_evaluation_function
eval_funct = sol3_heuristic.evaluation_function

constant_heuristic = Heuristic(
    name='Simple heuristic',
    evaluation_function=eval_funct,
)

try:
    if len(sys.argv)==3:
        #num_matches = int(sys.argv[1])
        depth = int(sys.argv[1])
        select_minimax = (sys.argv[2] == "minimax")
    else:
        raise Exception() 
except:
    print("Use: python3 time_minimax.py [max depth] [strategy]")
    print("Where [stategy] is either \"minimax\" or \"alpha-beta\"")
    exit(0)

if select_minimax:
    strategy_a = MinimaxStrategy(
        heuristic=constant_heuristic,
        max_depth_minimax=depth,
        verbose=0,
    )
    strategy_b = MinimaxStrategy(
        heuristic=constant_heuristic,
        max_depth_minimax=depth,
        verbose=0,
    )
else:
    strategy_a = MinimaxAlphaBetaStrategy(
        heuristic=constant_heuristic,
        max_depth_minimax=depth,
        verbose=0,
    )
    strategy_b = MinimaxAlphaBetaStrategy(
        heuristic=constant_heuristic,
        max_depth_minimax=depth,
        verbose=0,
    )

player_a = Player(
    name='Player_a',
    strategy=strategy_a,
)
player_b = Player(
    name='Player_b',
    strategy=strategy_b,
)

initial_player = player_a  # Player who moves first.

# Board at an intermediate state of the game.
initial_board = ([
    '........',
    '........',
    '........',
    '...BW...',
    '...WB...',
    '........',
    '........',
    '........'
])
initial_board = from_array_to_dictionary_board(initial_board)
height, width = 8, 8

# Initialize a reversi game.
game = Reversi(
    player1=player_a,
    player2=player_b,
    height=height,
    width=width,
)

# Initialize a game state.
game_state = TwoPlayerGameState(
    game=game,
    board=initial_board,
    initial_player=initial_player,
)

# Initialize a match.
match = TwoPlayerMatch(
    game_state,
    max_seconds_per_move=1000,
    gui=False,
)

# Play match

start_time = timeit.default_timer()

match.play_match()

end_time = timeit.default_timer()

print("Time:", end_time - start_time)
