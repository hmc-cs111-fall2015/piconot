package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 * @author aputman
 */
object PracticalTest extends JFXApp with PracticalBot {
  val rules = picobot {
    state ("toCorner") {
      move (left) {
        check(left -> free) -> "toCorner"
      }
      move (up) {
        check(left -> blocked, up -> free) -> "toCorner"
      }
      stay {
        check(left -> blocked, up -> blocked) -> "toRight"
      }
    }
    
    state ("toRight") {
      move (right) {
        check(right -> free) -> "toRight"
      }
      move (down) {
        check(right -> blocked, down -> free) -> "toLeft"
      }
    }
    
    state ("toLeft") {
      move (left) {
        check(left -> free) -> "toLeft"
      }
      move (down) {
        check(left -> blocked, down -> free) -> "toRight"
      }
    }
  }
  val maze = Maze("resources" + File.separator + "empty.txt")
  object RuleBot extends Picobot(maze, rules)
    with TextDisplay with GUIDisplay
  RuleBot.run()
  stage = RuleBot.mainStage
}