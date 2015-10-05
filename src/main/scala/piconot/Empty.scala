package piconot

import java.io.File
import scalafx.application.JFXApp

import piconot.language._


/**
 * @author dhouck apinson
 */
object Empty extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = (
    start state "goNorthEast"
       rule Nopen go North
       rule (Nclosed, Eopen) go East
       rule Anywhere turn West transition "Sweep"
    state "Sweep"
       rule Fopen go Forward
       rule Anywhere go South turn Back
  )
  println (rules: List[picolib.semantics.Rule])
  object EmptyBot extends Picobot(emptyMaze, rules)
      with TextDisplay with GUIDisplay
  
    EmptyBot.run()
  
    stage = EmptyBot.mainStage
}