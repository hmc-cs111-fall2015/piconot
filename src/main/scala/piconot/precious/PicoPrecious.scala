package piconot.precious

import scala.language.implicitConversions
import java.io.File
import picolib.maze.Maze
import picolib.semantics._

// We looked at and were inspired by Alex Gruver and Nathan Hall's implementation from the fall 2014 class

package object lotrSemantics {
  implicit def MazeBuilder(filename: String):Maze = {
    Maze("resources" + File.separator + filename);
  }
  
  
  
  def If: RuleBuilder = new RuleBuilder
  
  val hold = true
  val ready = false
  
  val go: Int = 0
  
  val Shire: MoveDirection = North
  val LonelyMountain: MoveDirection = East
  val UndyingLands: MoveDirection = West
  val Mordor: MoveDirection = South
  val stay: MoveDirection = StayHere
  
  val empty: RelativeDescription = Open
  val anything: RelativeDescription = Anything
  val Orcs: RelativeDescription = Blocked
  val orcs: RelativeDescription = Blocked
   
  implicit def intToState(toConvert: Int): State = {
    new State((toConvert-1).toString())
  }
  
  def PicoPrecious(maze: Maze, rules: RuleBuilder*): Picobot with TextDisplay with GUIDisplay = {
    rules.foreach { x => println(x) }
    val ruleList: List[Rule] = rules.toList.map { x => x.toRule }
    println(ruleList)
    
    object Gollumbot extends Picobot(maze, ruleList)
    with TextDisplay with GUIDisplay

    Gollumbot
  }
  
  
  
}
