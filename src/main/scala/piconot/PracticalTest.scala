package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 * @author aputman
 */
object PracticalTest extends PracticalBot {
  picobot (
    state ("0") (
      stay ("toCorner")
    ),
    state ("toCorner") (
      move (left),
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
}
