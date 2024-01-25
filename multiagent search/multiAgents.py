# multiAgents.py
# --------------
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


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent
from pacman import GameState

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState: GameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState: GameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
        foods = newFood.asList()

        closest_ghost = 99999999
        #ghost closes to pos 
        for ghost in newGhostStates:
            x,y = ghost.getPosition()
            new_dist = manhattanDistance((x,y), newPos)
            if ghost.scaredTimer > new_dist:
                temp =  999999
            elif ghost.scaredTimer <= 1:
                temp = -999999
            else:
                temp = 0
            closest_ghost = min(temp, closest_ghost)

        closest_food = 9999999
        if not foods:
            closest_food = 0
        else:
            for food in foods:
                food_dist = manhattanDistance(newPos, food)
                closest_food = min(closest_food,food_dist)
        
        return successorGameState.getScore() + closest_ghost + (1/(closest_food + 1))

def scoreEvaluationFunction(currentGameState: GameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState: GameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        
        depth = self.depth
        #base case, start at bottom depth and keep going until reach the top, or the game wins/loses
        
        def helper(agent_number, state, depth):
            if terminal_state(state, depth):
                return scoreEvaluationFunction(state), None
            elif agent_number == 0: #if the agent is pacman, then max func
                return max_func(agent_number,state,  depth)
            else: # if agent is anything else, return min func
                return min_func(agent_number, state, depth)

        def terminal_state(state, depth):
           return state.isLose() or state.isWin() or depth == 0


        def min_func(agent_number, state, depth):
            actions = state.getLegalActions(agent_number)
            agents_size = state.getNumAgents()
            #when we're at the last ghost, then we decrement depth, else depth is the same 
            if agent_number == agents_size - 1:
                agent = 0
                next_depth = depth - 1
            else: 
                agent = agent_number + 1 
                next_depth = depth 

            score = 9999999
            ret_action = None
            for action in actions:
                next_state = state.generateSuccessor(agent_number, action)
                next_score = helper(agent, next_state,next_depth)[0]
                if next_score < score:
                    score = next_score
                    ret_action = action
            return score, ret_action
        
        def max_func(agent_number, state, depth):
            actions = state.getLegalActions(agent_number)
            agents_size = state.getNumAgents()
            #when we're at the last ghost, then we decrement depth, else depth is the same 
            if agent_number == agents_size - 1:
                agent = 0
                next_depth = depth - 1
            else: 
                agent = agent_number + 1 
                next_depth = depth 

            score = -9999999
            ret_action = None
            for action in actions:
                next_state = state.generateSuccessor(agent_number, action)
                next_score = helper(agent, next_state, next_depth)[0]
                if next_score > score:
                    score = next_score
                    ret_action = action
            return score, ret_action

        return helper(0, gameState, depth)[1]
            
class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """
    
    def getAction(self, gameState: GameState):
        alpha = -999999
        beta = 99999
        # Get legal actions for the root agent (agent 0)
        legalMoves = gameState.getLegalActions(0)
        # Initialize best action and score
        bestAction = None
        bestScore = -99999

        for action in legalMoves:
            successor = gameState.generateSuccessor(0, action)
            newScore = self.min_func(successor, 0, 1, alpha, beta)
            
            if newScore > bestScore:
                bestScore = newScore
                bestAction = action
            alpha = max(alpha, bestScore)

        return bestAction

    def max_func(self, state, depth, alpha, beta):
        if depth == self.depth or state.isWin() or state.isLose():
            return self.evaluationFunction(state)

        v = -999999
        legalMoves = state.getLegalActions(0)

        for action in legalMoves:
            successor = state.generateSuccessor(0, action)
            v = max(v, self.min_func(successor, depth, 1, alpha, beta))
            
            if v > beta:
                return v
            
            alpha = max(alpha, v)
        
        return v

    def min_func(self, state, depth, agent_num, alpha, beta):
        if depth == self.depth or state.isWin() or state.isLose():
            return self.evaluationFunction(state)

        v = 99999
        legalMoves = state.getLegalActions(agent_num)
        numAgents = state.getNumAgents()

        for action in legalMoves:
            successor = state.generateSuccessor(agent_num, action)

            if agent_num == numAgents - 1:
                v = min(v, self.max_func(successor, depth + 1, alpha, beta))
            else:
                v = min(v, self.min_func(successor, depth, agent_num + 1, alpha, beta))
            
            if v < alpha:
                return v
            
            beta = min(beta, v)
        
        return v

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, state: GameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        legal_actions = state.getLegalActions(0)
        best_score = -99999
        best_action = None
        for action in legal_actions:
            successor = state.generateSuccessor(0, action)
            score = self.expectimax(successor, 0, 1)
            if score > best_score:
                best_score = score
                best_action = action
        return best_action
    
    def expectimax(self, state, depth, agent_num):
        if self.depth == depth or state.isLose() or state.isWin():
            return self.evaluationFunction(state)

        if agent_num == 0:
            return self.max_value(state, depth)
        
        return self.exp_value(state, depth, agent_num)

    def max_value(self, state, depth):
        max_score = -99999
        legal_actions = state.getLegalActions(0)
        for action in legal_actions:
            successor = state.generateSuccessor(0, action)
            score = self.expectimax(successor, depth, 1)
            max_score = max(max_score, score)
        return max_score

    def exp_value(self, state, depth, agent_num):
        total = 0
        legal_actions = state.getLegalActions(agent_num)
        num_actions = len(legal_actions)
        for action in legal_actions:
            successor = state.generateSuccessor(agent_num, action)
            if agent_num == state.getNumAgents() - 1:
                total += self.expectimax(successor, depth + 1, 0)
            else:
                total += self.expectimax(successor, depth, agent_num + 1)
        return total / num_actions


def betterEvaluationFunction(currentGameState: GameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    newPos = currentGameState.getPacmanPosition()
    newFood = currentGameState.getFood()
    newGhostStates = currentGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
    foods = newFood.asList()

    closest_ghost = 99999999
    #ghost closes to pos 
    for ghost in newGhostStates:
        x,y = ghost.getPosition()
        if ghost.scaredTimer == 0:
            ghost_dist = manhattanDistance((x, y),newPos)
            closest_ghost = min(closest_ghost,ghost_dist)
        else:
            closest_ghost = -10


    closest_food = 9999999
    if not foods:
        closest_food = 0
    else:
        for food in foods:
            food_dist = manhattanDistance(newPos, food)
            closest_food = min(closest_food,food_dist)
        
    return (currentGameState.getScore()-9/(closest_ghost+1)) - closest_food/4

    # newPos = currentGameState.getPacmanPosition()
    # newFood = currentGameState.getFood()
    # newGhostStates = currentGameState.getGhostStates()

    # foods = newFood.asList()

    # # Calculate closest ghost distance
    # non_scared_ghosts = (manhattanDistance(ghost.getPosition(), newPos) for ghost in newGhostStates if ghost.scaredTimer == 0)
    # closest_ghost = min(non_scared_ghosts, default=99999999)

    # # If any ghost is scared, consider its distance as -10, overriding any non-scared ghost distance
    # if any(ghost.scaredTimer > 0 for ghost in newGhostStates):
    #     closest_ghost = -10

    # # Calculate closest food distance
    # food_dists = (manhattanDistance(newPos, food) for food in foods)
    # closest_food = min(food_dists, default=0)

    # return currentGameState.getScore() + closest_ghost + (1 / (closest_food + 1))


    

# Abbreviation
better = betterEvaluationFunction
