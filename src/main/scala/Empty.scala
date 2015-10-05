package piconot
import scalafx.application.JFXApp
import picolib.maze.Maze
import picolib.semantics._
import java.io.File

/**
 * @author justisallen
 */
object Empty extends language{
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")
  val rules = List(
    rule (0) (Wo) -> (W),
    rule (0) (Wx, So) -> (S),
    rule (0) (Wx, Sx) -> (E, 1),
    rule (1) (Eo) -> (E),
    rule (1) (Ex) -> (N, 2),
    rule (2) (Wo) -> (W, 2),
    rule (2) (Wx) -> (N, 1)
  )
  object EmptyBot extends Picobot(emptyMaze, rules)
  with TextDisplay with GUIDisplay

  EmptyBot.run()
  
  stage = EmptyBot.mainStage
}
