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
       rule Anywhere turn West transition "testSouth"
    state "testSouth"
       // This detour is to test that chaining directions works correctly
       // It ends with the picobot in the same location as it started, ready to Sweep West
       rule Anywhere turn Left go South go Forward go South // Facing South
                     turn Right go Forward go Back go Right go Left // Facing West
                     turn Back go Left go North go Left // Facing East
                     turn West
                     transition "Sweep"
    state "Sweep"
       rule Fopen go Forward
       rule Anywhere go South turn Back
  )

  //  println (rules mkString "\n")
  
  object EmptyBot extends Picobot(emptyMaze, rules)
      with TextDisplay with GUIDisplay
  
    EmptyBot.run()
  
    stage = EmptyBot.mainStage
}