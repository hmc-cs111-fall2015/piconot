package piconot

import java.io.File
import scalafx.application.JFXApp

import piconot.language._


/**
 * @author dhouck apinson
 */
object RightHand extends JFXApp {
  val rightHandMaze = Maze("resources" + File.separator + "maze.txt")

  // This is the version of the right-hand rule with the fewest states
  val rulesMinimal = (
    start state "Start"
      rule Rclosed turn Left
      rule Anywhere turn Right go Forward
  )
  // This is a more verbose version of the right-hand rule but goes faster
  val rulesFast = (
    start state "Start"
      rule Ropen turn Right go Forward
      rule (Rclosed, Fopen) go Forward
      rule (Rclosed, Fclosed, Lopen) turn Left go Forward
      rule Anywhere turn Back go Forward
  )

  object RightHandBot extends Picobot(rightHandMaze, rulesFast)
      with TextDisplay with GUIDisplay
  
    RightHandBot.run()
  
    stage = RightHandBot.mainStage
}