package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 * @author aputman
 */
trait PracticalBot {
  // State - 
  def picobot(rs: Seq[Rule]*): List[Rule] = {
    rs.flatten.toList
  }
  
  def state (s: State) (moveResults: Seq[Rule]*): Seq[Rule] = {
    moveResults.flatten.map (_.copy(startState = s))
  }
  
  def move (dir: MoveDirection) (modifierResult: Rule*): Seq[Rule] = modifierResult match{
      //case Seq() => DefaultRule.copy(moveDirection = dir, surroundings)
      case notEmpty =>  notEmpty.map (_.copy(moveDirection = dir))
  }
  
  def stay (modifierResult: Rule*): Seq[Rule] = modifierResult match{
    //case Seq() => DefaultRule.copy(moveDirection = dir, surroundings)
    case notEmpty =>  notEmpty.map (_.copy(moveDirection = StayHere))
  }
  
  def check(args: (MoveDirection, RelativeDescription)*): Surroundings = {
    
    args.foldLeft(DefaultSurrounding) ((surr, arg) => 
                                        arg._1 match {
                                          case North => surr.copy(north = arg._2)
                                          case South => surr.copy(south = arg._2)
                                          case East => surr.copy(east = arg._2)
                                          case West => surr.copy(west = arg._2)
                                        })
  }
  
  implicit def stringToState(stateString: String): State = State(stateString)
  implicit def surrToRule(ruleTuple: (Surroundings, String)): Rule = {
    DefaultRule.copy(surroundings = ruleTuple._1, endState = ruleTuple._2)
  }
  
  val DefaultSurrounding: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  
  val DefaultRule: Rule = Rule( State("default"), 
                          DefaultSurrounding, 
                          StayHere, 
                          State("default"))
                          
  val n, north, up = North
  val s, south, down = South
  val e, east, right = East
  val w, west, left = West
  
  val free, open = Open
  val wall, blocked = Blocked
}


