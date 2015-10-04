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

  class CanMakeRule(posDirs : List[Direction],
                    negDirs : List[Direction]) {

    object ConstraintConflictException extends Error

    def makeRule(moveDir : semantics.MoveDirection, nextState : State) = {
      def getBlocked(d: Direction) = {
        val inN = negDirs contains d
        val inP = posDirs contains d

        if (inN && inP) throw ConstraintConflictException
        else if (inN) semantics.Open
        else if (inP) semantics.Blocked
        else          semantics.Anything
      }
      //println( getBlocked(N), getBlocked(E), getBlocked(W), getBlocked(S) )
      val surrs = semantics.Surroundings( getBlocked(N),
                                          getBlocked(E),
                                          getBlocked(W),
                                          getBlocked(S) )
      semantics.Rule(semantics.State("" + curState.hashCode()),
                     surrs, moveDir,
                     semantics.State("" + nextState.hashCode()))
    }
  }

  class EnvList(posDirs : List[Direction],
                negDirs : List[Direction]) extends CanMakeRule(posDirs, negDirs) {
    def +(dir : Direction) = { new EnvList(dir :: posDirs, negDirs) }
    def -(dir : Direction) = { new EnvList(posDirs, dir :: negDirs) }

    def ->(dir : Direction) : NeedsState = {
      rules += makeRule(dir.dir, curState)
      new NeedsState(posDirs, negDirs, dir)
    }
    def ->(state : State) : Unit = {
      rules += makeRule(semantics.StayHere, state)
    }
  }

  class NeedsState(posDirs : List[Direction],
                   negDirs : List[Direction],
                   moveDir : Direction ) extends CanMakeRule(posDirs, negDirs) {
    def and(nextState : State) = {
      rules(rules.length-1) = makeRule(moveDir.dir, nextState)
    }
  }

  class State() {
    def -(el: EnvList) = {curState = this; el}
  }
 
  def States = new State

  val N = new Direction(semantics.North)
  val E = new Direction(semantics.East)
  val W = new Direction(semantics.West)
  val S = new Direction(semantics.South)


  override def delayedInit(body : =>Unit) = {
    super.delayedInit{
      body // Create the states first

      println(rules.toList)
      object EmptyBot extends semantics.Picobot(maze, rules.toList)
        with semantics.TextDisplay with semantics.GUIDisplay

      EmptyBot.run()

      stage = EmptyBot.mainStage
    }
  }
}
