# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

#
# Authors: Pablo Cuesta Sierra and Álvaro Zamanillo Sáez (Group 2351. Pair 5.)
#

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()

def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

class Node:
    '''
        Node class: nodes used for the tree of the search function.
            state: state of the problem
            action: last action taken from the predecessor of the state to get to this state
            cost: total cost from the root to this state
            parentNode: points to the parent node
            path: list of actions from the root to the node 
    '''

    def __init__(self, state, action, cost, parentNode):
        self.state = state
        self.action = action
        self.cost = cost
        self.parentNode = parentNode

    @property
    def path(self):
        '''
            The path from the node to the root.
            Returns the list of actions from the beginning to the node.
        '''
        node,actions=self,[]

        while node.parentNode != None:
            actions.insert(0,node.action)
            node = node.parentNode
        return actions
    
    def __eq__(self, __o):
        if isinstance(__o, Node):
            return __o.state == self.state
        return False

def expandNode(problem, node, openList, closedList):
    '''
        Expands a node for the search algorithm
            problem: problem to be solved by the search function
            node: node to be expanded
            openList: openList of the algorithm
            closedList: container of the search function
    '''
    closedList.append(node)
    for successor in problem.getSuccessors(node.state):
        openList.push(Node(
                        state=successor[0],
                        action=successor[1],
                        cost=successor[2]+node.cost, 
                        parentNode=node))

def search(problem, openList):
    '''
        Search function that generalizes the different search types. 
            problem: problem to solve
            openList: the empty openList to be used in the implementation.
    '''
    closedList = []
    openList.push(Node(
            state=problem.getStartState(), 
            action=None, 
            cost=0, 
            parentNode=None))  # root node

    while not openList.isEmpty(): 
        currentNode = openList.pop()
        if problem.isGoalState(currentNode.state):
            return currentNode.path
        if currentNode not in closedList:
            expandNode(problem, currentNode, openList, closedList)
     
    return [] # No solution found  


def depthFirstSearch(problem):
    """Search the deepest nodes in the search tree first."""
    return search(problem, util.Stack())

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    return search(problem, util.Queue())

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    return aStarSearch(problem, nullHeuristic) 

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    return search(problem, 
                  util.PriorityQueueWithFunction(
                      lambda node: node.cost + heuristic(node.state, problem)))


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
