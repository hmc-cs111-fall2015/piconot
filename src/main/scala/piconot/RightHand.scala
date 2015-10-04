package piconot

import java.io.File
import scalafx.application.JFXApp

import piconot.language._


/**
 * @author dhouck apinson
 */
object RightHand extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = (
    start state "Start"
      rule Rclosed turn Left
      rule Anywhere turn Right go Forward
  )

//  object RightHandBot extends Picobot(emptyMaze, rules)
//      with TextDisplay with GUIDisplay
//  
//    RightHandBot.run()
//  
//    stage = RightHandBot.mainStage
}