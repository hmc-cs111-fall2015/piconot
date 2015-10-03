package piconot

import scalafx.application.JFXApp
import picolib.maze.Maze
import picolib.semantics
import scala.collection.mutable.MutableList
import scala.language.experimental.macros

trait PicoBot extends JFXApp {

  var maze: Maze = null;
  val rules: MutableList[semantics.Rule] = new MutableList
  var curState : State = null

  object use {
    def map(path: String) = {maze = Maze(path)}
  }
  //def map(path: String) = {maze = Maze(path)}

  class Direction(val dir : semantics.MoveDirection) {
    def unary_+ = { new EnvList(List(this), List()) }
    def unary_- = { new EnvList(List(), List(this)) }
  }

  class EnvList(posDirs : List[Direction],
                negDirs : List[Direction]) {
    def +(dir : Direction) = { new EnvList(dir :: posDirs, negDirs) } // Check if it's there already
    def -(dir : Direction) = { new EnvList(posDirs, dir :: negDirs) } // Check if it's there already
    def ->(dir : Direction) = { new NeedsState(posDirs, negDirs, dir) }
    def ->(state : State) = { new NeedsState(posDirs, negDirs, new Direction(semantics.StayHere)).and(state) }
  }

  class NeedsState(posDirs : List[Direction],
                   negDirs : List[Direction],
                   moveDir : Direction ) {
    def and(nextState : State) = {
      // Sry
      def makeWalls(posDirs : List[Direction], negDirs : List[Direction]): semantics.Surroundings = {
        def getBlocked(d: Direction) =
          if (negDirs contains d)      semantics.Open
          else if (posDirs contains d) semantics.Blocked
          else                         semantics.Anything
        println( getBlocked(N), getBlocked(E), getBlocked(W), getBlocked(S) )
        semantics.Surroundings( getBlocked(N), getBlocked(E), getBlocked(W), getBlocked(S) )
      }

      val newRule = semantics.Rule(semantics.State("" + curState.hashCode()),
                         makeWalls(posDirs, negDirs),
                         moveDir.dir,
                         semantics.State("" + nextState.hashCode()))
      rules += newRule
    }
  }

  class State() {
    //def apply() : Unit = {curState = this}
    def -(el: EnvList) = {curState = this; el}
    //def are(s : State) = { this = s }
  }
 
  def States = new State

  val N = new Direction(semantics.North)
  val E = new Direction(semantics.East)
  val W = new Direction(semantics.West)
  val S = new Direction(semantics.South)


  override def delayedInit(body : =>Unit) = {
    super.delayedInit{
      body // Create the states first

      object EmptyBot extends semantics.Picobot(maze, rules.toList) with semantics.TextDisplay with semantics.GUIDisplay

      EmptyBot.run()

      stage = EmptyBot.mainStage
    }
  }
}
