package piconot

import picolib.maze.Maze
import picolib.semantics._

trait PicoBot extends App {

  var rules: List[Rule] = List()
  var curState : State = null

  class Direction(var dir : MoveDirection) {
    def unary_+ = { new EnvList(List(this), List()) }
    def unary_- = { new EnvList(List(), List(this)) }
  }

  class EnvList(posDirs : List[Direction],
                negDirs : List[Direction]) {
    def +(dir : Direction) = { new EnvList(dir :: posDirs, negDirs) } // Check if it's there already
    def -(dir : Direction) = { new EnvList(posDirs, dir :: negDirs) } // Check if it's there already
    def ->(dir : Direction) = { new NeedsState(posDirs, negDirs, dir) }
    def ->(state : State) = { new NeedsState(posDirs, negDirs, new Direction(StayHere)).and(state) }
  }

  class NeedsState(posDirs : List[Direction],
                   negDirs : List[Direction],
                   moveDir : Direction ) {
    def and(nextState : State) = {
      // Sry
      def makeWalls(posDirs : List[Direction], negDirs : List[Direction]): Surroundings = {
        def getBlocked(d: Direction) = 
          if (posDirs contains d)      Open 
          else if (negDirs contains d) Blocked
          else                         Anything
        Surroundings( getBlocked(N), getBlocked(E), getBlocked(W), getBlocked(S) )
      }
      val newRule = Rule(State("" + curState.hashCode()),
                         makeWalls(posDirs, negDirs),
                         moveDir.dir, 
                         State("" + nextState.hashCode()))
      rules = newRule :: rules
    }
  }

  class State() {
    def rules = {curState = this}
  }

  val N = new Direction(North)
  val E = new Direction(East)
  val W = new Direction(West)
  val S = new Direction(South)
}


class PicoFSM extends PicoBot {

  val starting = new State
  val goEast = new State
  val downSweep = new State
  val upSweep = new State

  starting rules;
  -N -> N and starting
  +N -> goEast

  goEast rules;
  -E +N -> E and goEast
  +E +N -> downSweep

  downSweep rules;
  -S -> S and downSweep
  +S -> W and upSweep

  upSweep rules;
  -N -> N and upSweep
  +S -> W and downSweep
}
