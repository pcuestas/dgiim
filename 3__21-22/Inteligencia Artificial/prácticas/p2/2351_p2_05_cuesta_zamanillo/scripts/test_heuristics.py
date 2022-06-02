"""
File used to test heuristics that are later used in 
demo_tournament to play games against each other.

Authors: 
    Pablo Cuesta Sierra 
    Álvaro Zamanillo Sáez

"""
from __future__ import annotations  # For Python 3.7

import copy
import numpy as np

from game import TwoPlayerGameState
from heuristic import simple_evaluation_function
from reversi import (
    Reversi,
    from_array_to_dictionary_board,
    from_dictionary_to_array_board,
)
from tournament import StudentHeuristic


############################################################
##          AUX FUNCTIONS                                 ##
############################################################

#https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/RUSSIA/Final_Paper.pdf
weights1 = [
    [ 4, -3,  2,  2,  2,  2, -3,  4],
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [ 2, -1,  1,  0,  0,  1, -1,  2],
    [ 2, -1,  0,  1,  1,  0, -1,  2],
    [ 2, -1,  0,  1,  1,  0, -1,  2],
    [ 2, -1,  1,  0,  0,  1, -1,  2],
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [ 4, -3,  2,  2,  2,  2, -3,  4]
]
#https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/O-Thell-Us/Othellus.pdf
weights2 = [
    [100,-10, 11,  6,  6, 11,-10,100],
    [-10,-20,  1,  2,  2,  1,-20,-10],
    [ 10,  1,  5,  4,  4,  5,  1, 10],
    [  6,  2,  4,  2,  2,  4,  2,  6],
    [  6,  2,  4,  2,  2,  4,  2,  6],
    [ 10,  1,  5,  4,  4,  5,  1, 10],
    [-10,-20,  1,  2,  2,  1,-20,-10],
    [100,-10, 11,  6,  6, 11,-10,100],
]

weights3 = [
    [120,-20, 20,  5,  5, 20,-20,120],
    [-20,-40, -5, -5, -5, -5,-40,-20],
    [ 20, -5, 15,  3,  3, 15, -5, 20],
    [  5, -5,  3,  3,  3,  3, -5,  5],
    [  5, -5,  3,  3,  3,  3, -5,  5],
    [ 20, -5, 15,  3,  3, 15, -5, 20],
    [-20,-40, -5, -5, -5, -5,-40,-20],
    [120,-20, 20,  5,  5, 20,-20,120],
]


def player_coins(board: dict, player_label: Any) -> float:
    return sum(x == player_label for x in board.values())

def coin_diff(state: TwoPlayerGameState) -> float:
    """Difference in the number of coins."""
    total = len(state.board)
    if total:
        return 100 * (player_coins(state.board, state.player1.label) - player_coins(state.board, state.player2.label)) / total
    else:
        return 0
    
def static_weights_player(board: dict, player_label: Any, weights):
    return sum(
            weights[key[0] - 1][key[1] - 1]
            for key in board.keys() if (board[key] == player_label)
        )

def static_weights_heuristic(state: TwoPlayerGameState, weights) -> float:
    ''' 100 * (sum of p1's weights - sum of p2's weights) / (# of coins in the board) '''
    return 100 *                                                                                    \
            (static_weights_player(state.board, state.player1.label, weights)                       \
           - static_weights_player(state.board, state.player2.label, weights)) / len(state.board) 

def corner_diff(state: TwoPlayerGameState) -> float:
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

def unoccupied(i,j,board) -> bool:
    '''whether position i,j of the board array is occupied, False if it is out of bounds'''
    if (i < 0) or (j < 0) or (i >= 8) or (j >= 8):
        return False 
    return board[i][j] == '.'

def is_frontier(i,j, board) -> bool:
    surrounding = [
                (i-1,j-1),(i  ,j-1),(i+1,j-1),
                (i-1,j  ),          (i+1,j  ),
                (i-1,j+1),(i  ,j+1),(i+1,j+1)
        ]
    return any(unoccupied(i,j,board) for (i,j) in surrounding)

def frontiers_player(state: TwoPlayerGameState, label: Any) -> float:
    board_arr = from_dictionary_to_array_board(state.board, 8, 8)
    arr_pieces = [(j-1,i-1) for (i,j) in state.board.keys() if state.board[(i,j)] == label]
    return sum(is_frontier(i,j, board_arr) for (i,j) in arr_pieces)

def frontiers(state: TwoPlayerGameState) -> float:
    p1,p2 = frontiers_player(state, state.player1.label), frontiers_player(state, state.player2.label)
    if (p1+p2):
        return 100*(p2-p1)/(p1+p2)
    else:
        return 0
##############################################################################
# test frontiers:
    #initial_board = ([
    #    '..BBB...',
    #    '..BBB...',
    #    '..BBB...',
    #    '........',
    #    '..B.....',
    #    '..BW....',
    #    '........',
    #    '........'
    #])
    #
    #state = TwoPlayerGameState()
    #
    #state.board = from_array_to_dictionary_board(initial_board)
    #
    #print(frontiers_player(state, 'B'))
    #
    #exit(0) 
##############################################################################
####################################################################################

def corner_capture_situation(state: TwoPlayerGameState,player1_label,player2_label) -> float:
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
                # Player 2 has direct access to the corner
                if   board[values[0+i]] == player1_label and board[values[1+i]] == player2_label:
                    n -= 1
                # Player 1 has direct access to the corner
                elif board[values[0+i]] == player2_label and board[values[1+i]] == player1_label:
                    n += 1
            except:
                continue
    return n

############################################################
##          END OF AUX FUNCTIONS                          ##
############################################################


class Solution2_old(StudentHeuristic):
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

  def weights_around_corner(self,weights: list[list[int]], corner, factor):
    for (i,j) in [(1,0),(-1,0),(0,1),(-1,0)]:
      try:
        weights[corner[0]+i-1][corner[1]+j-1]=factor*abs(weights[corner[0]+i-1][corner[1]+j-1])
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

class CoinDiffHeuristic(StudentHeuristic):
  def get_name(self) -> str:
    return "cz_solution2"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
    
  def utility(self, state: TwoPlayerGameState) -> float:
    return coin_diff(state)

class HeuristicMobility(StudentHeuristic):
    def get_name(self) -> str:
        return "Mobility"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_

    def utility(self, state: TwoPlayerGameState) -> float:
        return  0.3 * coin_diff(state)                      \
                + 0.4 * corner_diff(state)                  \
                + 0.3 * frontiers(state)

class HeuristicDontGiveCorner(StudentHeuristic):
    def get_name(self) -> str:
        return "DontGiveCorner"
    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
        
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return  0.6 * coin_diff(state)                      \
                + 0.4 * corner_diff(state)                  \
                +20 * corner_capture_situation(state, state.player1.label, state.player2.label)

class EdgeStability(StudentHeuristic):
  def get_name(self) -> str:
    return "EdgeStability"
  def evaluation_function(self, state: TwoPlayerGameState) -> float:
    if state.end_of_game:
      diff = coin_diff(state)
      utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
    else:    
      utility_ = self.utility(state)    
    return utility_ if state.is_player_max(state.player1) else - utility_
  def utility(self, state: TwoPlayerGameState) -> float:
    return  0.25 * coin_diff(state)                    \
          + 0.4 * corner_diff(state)                  \
          + 0.20 * frontiers(state)                    \
          + 0.15 *self.edge_stability(state)

  def edge_stability(self,state: TwoPlayerGameState):
        board=state.board
        corners=[(1,1),(1,8),(8,1),(8,8)]
        taken_corners=list(set(corners) & set(board.keys()))

        directions={(1,1): [(1,0),(0,1)],
                    (1,8): [(1,0),(0,-1)],
                    (8,1): [(-1,0),(0,1)],
                    (8,8): [(-1,0),(0,-1)]}

        p1,p2=0,0

        for corner in taken_corners:
            if board[corner]==state.player1.label:
                p1+=self.stable_edge(state,corner,directions[corner],state.player1.label) #Full columns will be counted twice
            else:
                p2+=self.stable_edge(state,corner,directions[corner],state.player2.label)

        return 0 if p1+p2==0 else 100*(p1-p2)/(p1+p2)

    # Counts the number of stable coins in one edge
  def stable_edge(self,state: TwoPlayerGameState,corner, directions, player_label):
        n=0
        board=state.board

        for dir in directions:
            pos=(corner[0]+dir[0],corner[1]+dir[1])
            while pos in board.keys() and board[pos] == player_label:
                n+=1
                pos=(pos[0]+dir[0],pos[1]+dir[1])

        return n

class HeuristicModify(StudentHeuristic):
    def get_name(self) -> str:
        return "Modify"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return static_weights_heuristic(state,self.weights_modify(weights2,state))


    def weights_around_corner(self,weights: list[list[int]], corner, factor):

        for (i,j) in [(1,0),(-1,0),(0,1),(-1,0)]:
            try:
                weights[corner[0]+i-1][corner[1]+j-1]=factor*abs(weights[corner[0]+i-1][corner[1]+j-1])
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

class HeuristicOnlyWeight1(StudentHeuristic):
    def get_name(self) -> str:
        return "only_weight1"

    def evaluation_function(self, state: TwoPlayerGameState) -> float: 
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return static_weights_heuristic(state,weights1)

class HeuristicOnlyWeight2(StudentHeuristic):
    def get_name(self) -> str:
        return "only_weight2"

    def evaluation_function(self, state: TwoPlayerGameState) -> float: 
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return static_weights_heuristic(state,weights2)

class HeuristicOnlyWeight3(StudentHeuristic):
    def get_name(self) -> str:
        return "only_weight2"

    def evaluation_function(self, state: TwoPlayerGameState) -> float: 
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return static_weights_heuristic(state,weights3)

class Heuristic1(StudentHeuristic):
    def get_name(self) -> str:
        return "1stH"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return    0.3 * coin_diff(state)                                \
                + 0.3 * corner_diff(state)                              \
                + 0.4 * static_weights_heuristic(state, weights1)

class Heuristic2(StudentHeuristic):    
    def get_name(self) -> str:
        return "2ndH"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if state.end_of_game:
            diff = coin_diff(state)
            utility_ = 10000 if diff>0 else (-10000 if diff<0 else 0 )
        else:    
            utility_ = self.utility(state)    
        return utility_ if state.is_player_max(state.player1) else - utility_
    
    def utility(self, state: TwoPlayerGameState) -> float:
        return    0.6 * coin_diff(state)                       \
                + 0.4 * corner_diff(state) 
				
class Heuristic3(StudentHeuristic):
    def get_name(self) -> str:
        return "random"
    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        return float(np.random.rand())

class Heuristic4(StudentHeuristic):
    def get_name(self) -> str:
        return "heuristic"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        return simple_evaluation_function(state)

class tournament3_OnlyWeights(StudentHeuristic):
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
    coins = len(state.board)
    if coins < 20:
      return self.h_start(state)
    else:
      return self.h_intermediate(state)

  def h_start(self,state:TwoPlayerGameState) -> float:
    return self.static_weights_heuristic(state)

  def h_intermediate(self,state:TwoPlayerGameState) -> float:
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

############### final heuristics #########################################

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
      