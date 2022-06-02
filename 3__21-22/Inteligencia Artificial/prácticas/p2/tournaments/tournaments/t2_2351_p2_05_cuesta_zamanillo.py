from __future__ import annotations  # For Python 3.7

from game import (
    TwoPlayerGameState,
)

from tournament import (
    StudentHeuristic,
)
from reversi import from_dictionary_to_array_board
import copy

class tournament2_Solution1(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_solution1"
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


class tournament2_Solution2(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_solution2"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = self.coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
    
  def utility(self, state: TwoPlayerGameState) -> float:
    return self.static_weights_heuristic(state)


  #Modify the weights of the edges if a chain to a corner exists.  
  def weights_modify(self,weights: list[list[int]], state: TwoPlayerGameState) -> list[list[int]]:
    board=state.board
    corners=[(1,1),(1,8),(8,1),(8,8)]
    my_weights=copy.deepcopy(weights)
    taken_corners=list(set(corners) & set(board.keys()))

    corner_weight=weights[0][0]
 
    directions={(1,1): [(1,0),(0,1)],
                (1,8): [(1,0),(0,-1)],
                (8,1): [(-1,0),(0,1)],
                (8,8): [(-1,0),(0,-1)]}


    for corner in taken_corners:
        if board[corner]==state.player1.label:
            self.edge_weights(my_weights,state,corner,directions[corner],state.player1.label,corner_weight) #PLayer 1 has the corner
        else:
            self.edge_weights(my_weights,state,corner,directions[corner],state.player2.label,corner_weight)

    return my_weights


  def edge_weights(self,weights,state: TwoPlayerGameState,corner, directions,player_label,new_weight):
       board=state.board

       for dir in directions:
            pos=(corner[0]+dir[0],corner[1]+dir[1])

            while pos in board.keys() and board[pos] == player_label:
                weights[pos[0]-1][pos[1]-1]=new_weight
                pos=(pos[0]+dir[0],pos[1]+dir[1])

  def static_weights_player(self, board: dict, player_label: Any, weights) -> float:
    return sum(
            weights[key[0] - 1][key[1] - 1]
            for key in board.keys() if (board[key] == player_label)
  )    

  
  def coin_diff(self, state: TwoPlayerGameState) -> float:
    """Difference in the number of coins."""
    total = len(state.board)
    if total:
      return 100 *                                                            \
            (sum(x == state.player1.label for x in state.board.values())      \
            -sum(x == state.player2.label for x in state.board.values())) / total
    else:
      return 0


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
    weights_p1 = self.weights_modify(weights,state) 

    return                                                                                  \
            (self.static_weights_player(state.board, state.player1.label, weights_p1)                       \
          - self.static_weights_player(state.board, state.player2.label, weights_p1)) 


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
