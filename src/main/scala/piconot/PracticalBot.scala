package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 * @author aputman
 */
class PracticalBot extends JFXApp{
  // State - 
  def picobot(rs: Seq[Rule]*): List[Rule] = {
    println(rs)
    val rules = rs.flatten.toList
    println(rules)
    
    val maze = Maze("resources" + File.separator + "empty.txt")
    object RuleBot extends Picobot(maze, rules) with GUIDisplay
      //with TextDisplay with GUIDisplay
    RuleBot.run()
    stage = RuleBot.mainStage
    
    rules
  }
  
  def state (s: State) (moveResults: Seq[Rule]*): Seq[Rule] = {
    moveResults.flatten.map {rule =>
      rule.endState match {
        case UndefinedState => rule.copy(startState = s, endState = s)
        case _ => rule.copy(startState = s)
      }
    }
  }
  
  class move(dir: MoveDirection, checked: Seq[MoveDirection]) {
    def apply(modifierResult: Rule*): Seq[Rule] = modifierResult match {
        case Seq() => move(dir)(continue)
        case notEmpty =>  notEmpty.map {rule =>
          val surroundings = updateSurroundings(rule.surroundings, checked map (d => d -> blocked))
          println(rule.surroundings); println(surroundings)
          val surr = updateSurroundings(surroundings, Seq(dir -> free))
          rule.copy(moveDirection = dir, surroundings = surr)
        }
    }
  }
  implicit def defaultMove(mv: move): Seq[Rule] = mv()
  object move {
    def apply(dir: MoveDirection, modifier: Seq[MoveDirection] = Seq()): move = new move(dir, modifier)
  }
  
  def stay (modifierResult: Rule*): Seq[Rule] = modifierResult match {
    //case Seq() => DefaultRule.copy(moveDirection = dir, surroundings)
    case notEmpty =>  notEmpty.map (_.copy(moveDirection = StayHere))
  }
  
  def check(changes: (MoveDirection, RelativeDescription)*): Surroundings = {
    updateSurroundings(DefaultSurroundings, changes)
  }
  
  def updateSurroundings(surr: Surroundings, changes: Seq[(MoveDirection, RelativeDescription)]): Surroundings = {
    changes.foldLeft(surr){
      (surr, change) => 
          change match {
            case (North, desc) => surr.copy(north = desc)
            case (South, desc) => surr.copy(south = desc)
            case (East, desc) => surr.copy(east = desc)
            case (West, desc) => surr.copy(west = desc)
          }
    }
  }
  
  implicit def stringToState(stateString: String): State = State(stateString)
  implicit def surrTupleToRule(ruleTuple: (Surroundings, String)): Rule = {
    DefaultRule.copy(surroundings = ruleTuple._1, endState = ruleTuple._2)
  }
  implicit def stateStringToRule(stateString: String): Rule = {
    DefaultRule.copy(endState = stateString)
  }
  implicit def surrToRule(surr: Surroundings): Rule = {
    DefaultRule.copy(surroundings = surr)
  }
  
  val DefaultSurroundings: Surroundings = Surroundings(Anything, Anything, Anything, Anything)

  val UndefinedStateString: String = "!!__ UN DE FI NE D __!!"
  val UndefinedState: State = State(UndefinedStateString)
  val continue: String = UndefinedStateString
  
  val DefaultRule: Rule = Rule(UndefinedState, 
                          DefaultSurroundings, 
                          StayHere, 
                          UndefinedState)
                          
  val n, north, up = North
  val s, south, down = South
  val e, east, right = East
  val w, west, left = West
  
  val free, open = Open
  val wall, blocked = Blocked
}


