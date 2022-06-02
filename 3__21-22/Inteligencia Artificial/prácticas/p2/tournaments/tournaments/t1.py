from __future__ import annotations  # For Python 3.7
import time
from game import (
    TwoPlayerGameState,
)
from tournament import (
    StudentHeuristic,
)
from reversi import from_dictionary_to_array_board
import copy

class Solution1(StudentHeuristic):
  def get_name(self) -> str:
    return "solution1"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
  def utility(self, state: TwoPlayerGameState) -> float:
    return  0.3 * self.coin_diff(state)                    \
          + 0.4 * self.corner_diff(state)                  \
          + 0.3 * self.frontiers(state)

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
    return "solution2"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
    
  def utility(self, state: TwoPlayerGameState) -> float:
    return self.static_weights_heuristic(state)

  def weights_around_corner(self,weights: list[list[int]], corner, factor):
    for (i,j) in [(1,0),(-1,0),(0,1),(-1,0)]:
      try:
        weights[corner[0]+i][corner[1]+j]=factor*abs(weights[corner[0]+i][corner[1]+j])
      except:
        continue
      
  def weights_modify(self,weights: list[list[int]], state: TwoPlayerGameState, pos_factor=2, neg_factor=2) -> list[list[int]]:
    #Assume we play in 8x8
    board=state.board
    my_weights=copy.deepcopy(weights)

    corners=[(1,1),(1,8),(8,1),(8,8)]
    for corner in corners:
        if corner in state.board.keys(): #The corner is occupied
            if board[corner] == state.player1.label: #Squares adjacent to corners are no longer bad options
                self.weights_around_corner(my_weights,corner, pos_factor) #Negative so -pos_factor*value >0
            else:
                self.weights_around_corner(my_weights,corner,-neg_factor)
    return my_weights

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
          [ 4, -3,  2,  2,  2,  2, -3,  4],
          [-3, -4, -1, -1, -1, -1, -4, -3],
          [ 2, -1,  1,  0,  0,  1, -1,  2],
          [ 2, -1,  0,  1,  1,  0, -1,  2],
          [ 2, -1,  0,  1,  1,  0, -1,  2],
          [ 2, -1,  1,  0,  0,  1, -1,  2],
          [-3, -4, -1, -1, -1, -1, -4, -3],
          [ 4, -3,  2,  2,  2,  2, -3,  4]
    ]
    weights = self.weights_modify(weights,state,)
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
    return "solution3"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    p1=0
    p2=0

    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:
      p1= -15 * self.dont_give_corner(state,state.player1.label,state.player2.label)
      p2= -15 * self.dont_give_corner(state, state.player2.label, state.player2.label)
      utility_ = self.utility(state)    
    return utility_+p1 if state.is_player_max(state.player1) else - utility_+p2
  

  def utility(self, state: TwoPlayerGameState) -> float:
    return  0.3 * self.coin_diff(state)       \
          + 0.4 * self.corner_diff(state)     \
          + 0.3 * self.frontiers(state)                    

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
            (self.static_weights_player(state.board, state.player1.label, weights)                  \
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

  def dont_give_corner(self, state: TwoPlayerGameState,player1_label,player2_label) -> float:
    board=state.board
    
    #Dictionary indexed by the corner:
    #  | val[1]       val[5]
    #  | val[0] val[4]
    #  | key    val[2] val[3]
    #   -----------------------
    corners={(1,1):[(1,2),(1,3),(2,1),(3,1),(2,2),(3,3)],
            (1,8):[(1,7),(1,6),(2,8),(3,8),(2,7),(3,6)],
            (8,1):[(7,1),(6,1),(8,2),(8,3),(7,2),(6,3)],
            (8,8):[(7,8),(6,8),(8,7),(8,6),(7,7),(6,6)]}

    n = 0
    for (corner, values) in corners.items():
      if corner in board.keys():  # The corner is already taken
        continue

      for i in range(3):
        try:
          # Player 2 has access to the corner
          if   board[values[0+i]] == player1_label and board[values[1+i]] == player2_label:
            n += 1
        except:
          continue
    return n
