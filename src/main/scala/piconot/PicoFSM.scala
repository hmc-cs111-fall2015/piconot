//package piconot

//import picolib.maze.Maze
//import picolib.semantics._

//class Direction(var dir : MoveDirection) {
class Direction(var dir : Char) {
  def unary_+ = { new EnvList(List(), List(this), List()) }
  def unary_- = { new EnvList(List(), List(), List(this)) }
}

class EnvList(prevRules : List[Rule],
              posDirs : List[Direction],
              negDirs : List[Direction]) {
  def +(dir : Direction) = { new EnvList(prevRules, dir :: posDirs, negDirs) } // Check if it's there already
  def -(dir : Direction) = { new EnvList(prevRules, posDirs, dir :: negDirs) } // Check if it's there already
  //def ->(dir : Direction) = { new NeedsState(posDirs, negDirs, dir) }
  def ->(dir : Direction) = { new NeedsState(posDirs, negDirs, dir) }
}

class NeedsState(prevRules : List[Rule],
                 posDirs : List[Direction],
                 negDirs : List[Direction],
                 moveDir : Direction ) {
  def and(nextState : String) = { new FinisedRule(prevRules, posDirs, negDirs, moveDir, nextState) }
}

class FinisedRule(prevRules : List[Rule],
                  posDirs : List[Direction],
                  negDirs : List[Direction],
                  moveDir : Direction,
                  nextState : String) {
def makeRule(prevRules : List[Rule],
                  posDirs : List[Direction],
                  negDirs : List[Direction],
                  moveDir : Direction,
                  nextState : String) {
  def +(dir : Direction) = {
    newrule = Rule(State("hello"),
    new EnvList(prevRules, dir :: posDirs, negDirs) }
  def -(dir : Direction) = { new EnvList(prevRules, posDirs, dir :: negDirs) }
}

//class Rule(posDirs : List[Direction],
//           negDirs : List[Direction],
//           moveDir : Direction,
//           nextState : String)

trait PicoBot extends App {
  val N = new Direction('N')
  val E = new Direction('E')
  val W = new Direction('W')
  val S = new Direction('S')

  def state(x: FinisedRule) = {}
}


class PicoFSM extends PicoBot {
  state{
    -N -E +S -> E and "sup"
  }
}
