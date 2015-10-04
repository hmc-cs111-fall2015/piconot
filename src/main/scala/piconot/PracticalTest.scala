package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 * @author aputman
 */
object PracticalTest extends JFXApp with PracticalBot {
  val rules = picobot (
    state ("0") (
      stay ("toCorner")
    ),
    state ("toCorner") (
      move (left)(),
      move (up) {
        check(left -> blocked)
      },
      stay {
        check(left -> blocked, up -> blocked) -> "toRight"
      }
    ),
    
    state ("toRight") (
      move (right)(),
      move (down) {
        check(right -> blocked) -> "toLeft"
      }
    ),
    
    state ("toLeft") (
      move (left)(),
      move (down) {
        check(left -> blocked) -> "toRight"
      }
    )
  )
  val maze = Maze("resources" + File.separator + "empty.txt")
  object RuleBot extends Picobot(maze, rules)
    with TextDisplay with GUIDisplay
  RuleBot.run()
  stage = RuleBot.mainStage
}