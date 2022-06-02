from __future__ import annotations  # For Python 3.7

from game import (
    TwoPlayerGameState,
)

from tournament import (
    StudentHeuristic,
)
from reversi import from_dictionary_to_array_board

class Solution1(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_sol1"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else (-utility_)
    
  def utility(self, state: TwoPlayerGameState) -> float:
    coins = len(state.board)
    if coins < 40:
      return self.h_start(state)
    else:
      return self.h_intermediate(state)

  def h_intermediate(self,state:TwoPlayerGameState) -> float:
    return  0.2 * self.coin_diff(state)                    \
          + 0.4 * self.corner_diff(state)                  \
          + 0.4 * self.frontiers(state)

  def h_start(self, state: TwoPlayerGameState) -> float:
    return self.static_weights(state)
  
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

  def static_weights(self, state: TwoPlayerGameState) -> float:
    ''' (sum of p1's weights - sum of p2's weights) '''
    board_value1 = [[4000, -2,  3,  2,  2, 3, -2, 4000],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [4000, -2,  3,  2,  2,  3, -2, 4000]]
    return (self.static_weights_player(state.board, state.player1.label, board_value1)                       \
          - self.static_weights_player(state.board, state.player2.label, board_value1))
	
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
	  
  ##### FRONTIERS ####################################################################
  def unoccupied(self, i,j,board) -> bool:
    '''whether position i,j of the board array is occupied, False if it is out of bounds'''
    if (i < 0) or (j < 0) or (i >= 8) or (j >= 8):
      return False 
    return board[i][j] == '.'

  def is_frontier(self, i,j, board) -> bool:
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

class Solution2(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_sol2"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else (-utility_)
    
  def utility(self, state: TwoPlayerGameState) -> float:
    coins = len(state.board)
    if coins < 25:
      return self.h_start(state)
    else:
      return self.h_intermediate(state)

  def h_intermediate(self,state:TwoPlayerGameState) -> float:
    return  0.2 * self.coin_diff(state)                    \
          + 0.4 * self.corner_diff(state)                  \
          + 0.4 * self.frontiers(state)

  def h_start(self, state: TwoPlayerGameState) -> float:
    return self.static_weights(state)
  
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

  def static_weights(self, state: TwoPlayerGameState) -> float:
    ''' (sum of p1's weights - sum of p2's weights) '''
    board_value1 = [[4000, -2,  3,  2,  2, 3, -2, 4000],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [4000, -2,  3,  2,  2,  3, -2, 4000]]
    return (self.static_weights_player(state.board, state.player1.label, board_value1)                       \
          - self.static_weights_player(state.board, state.player2.label, board_value1))
	
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
	  
  ##### FRONTIERS ####################################################################
  def unoccupied(self, i,j,board) -> bool:
    '''whether position i,j of the board array is occupied, False if it is out of bounds'''
    if (i < 0) or (j < 0) or (i >= 8) or (j >= 8):
      return False 
    return board[i][j] == '.'

  def is_frontier(self, i,j, board) -> bool:
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
      
class Solution3(StudentHeuristic):    
  def get_name(self) -> str:
    return "cz_sol3"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else (-utility_)
    
  def utility(self, state: TwoPlayerGameState) -> float:
    coins = len(state.board)
    if coins < 40:
      return self.h_start(state)
    else:
      return self.h_intermediate(state)

  def h_intermediate(self,state:TwoPlayerGameState) -> float:
    return  0.2 * self.coin_diff(state)                    \
          + 0.4 * self.corner_diff(state)                  \
          + 0.4 * self.frontiers(state)

  def h_start(self, state: TwoPlayerGameState) -> float:
    return self.static_weights(state) + 0.9 * state.game._choice_diff(state.board)
  
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

  def static_weights(self, state: TwoPlayerGameState) -> float:
    ''' (sum of p1's weights - sum of p2's weights) '''
    board_value1 = [[4000, -2,  3,  2,  2, 3, -2, 4000],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [4000, -2,  3,  2,  2,  3, -2, 4000]]
    return (self.static_weights_player(state.board, state.player1.label, board_value1)                       \
          - self.static_weights_player(state.board, state.player2.label, board_value1))
	
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
	  
  ##### FRONTIERS ####################################################################
  def unoccupied(self, i,j,board) -> bool:
    '''whether position i,j of the board array is occupied, False if it is out of bounds'''
    if (i < 0) or (j < 0) or (i >= 8) or (j >= 8):
      return False 
    return board[i][j] == '.'

  def is_frontier(self, i,j, board) -> bool:
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
      