package piconot
import scalafx.application.JFXApp
import picolib.maze.Maze
import picolib.semantics._
import java.io.File

/**
 * @author justisallen
 */
object RightHand extends language{
  val maze = Maze("resources" + File.separator + "maze.txt")
  val rules = List(
      //north-facing
    rule (0) (Ex,No) -> (N),
    rule (0) (Nx, Ex) -> (X, 2),
    rule (0) (Eo) -> (E, 1),
      //east facing
    rule (1) (Sx,Eo) -> (E),
    rule (1) (Ex,Sx) -> (X, 0),
    rule (1) (So) -> (S, 3),
      //west-facing
    rule (2) (Nx,Wo) -> (W),
    rule (2) (Nx,Wx) -> (X, 3),
    rule (2) (No) -> (N, 0),
      //south-facing
    rule (3) (Wx,So) -> (S),
    rule (3) (Wx,Sx) -> (X, 1),
    rule (3) (Wo) -> (W, 2)
  )
  object EmptyBot extends Picobot(maze, rules)
  with TextDisplay with GUIDisplay

  EmptyBot.run()
  
  stage = EmptyBot.mainStage
}
