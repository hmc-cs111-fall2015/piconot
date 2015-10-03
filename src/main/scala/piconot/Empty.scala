package piconot

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._
import piconot.precious.lotrSemantics._
import piconot.precious.locations._
import piconot.precious.weapons._
import piconot.precious._

object Empty extends JFXApp {  
  
  
  PicoPrecious("empty.txt", 
      If you hold weapon 1
      and Shire is empty
      and LonelyMountain has anything
      and UndyingLands is empty
      and Mordor has anything
      then go towards UndyingLands and ready weapon 1,
      
//      If you hold weapon 1,
//      and there is anything towards the Shire, 
//      anything towards the Lonely Mountain, 
//      orcs towards the Undying Lands, 
//      and anything towards Mordor, 
//      go towards the Shire and ready weapon 2.
//      
//      # State 1: move up
//      If you hold weapon 2,
//      and there is nothing towards the Shire,
//      anything towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and anything towards Mordor,
//      go towards the Shire and ready weapon 2.
//      
//      If you hold weapon 1,
//      and there are orcs towards the Shire,
//      anything towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and nothing towards Mordor,
//      go towards Mordor and ready weapon 3.
//      
//      # States 3 and 4: fill from top to bottom, left to right
//      # State 3: fill this column to the bottom
//      If you hold weapon 3,
//      and there is anything towards the Shire,
//      anything towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and nothing towards Mordor,
//      go towards Mordor and ready weapon 3.
//      
//      If you hold weapon 3,
//      and there is anything towards the Shire,
//      nothing towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and orcs towards Mordor,
//      go towards the Lonely Mountain and ready weapon 4.
//      
//      # State 4: fill this column to the top
//      If you hold weapon 4,
//      and there is nothing towards the Shire,
//      anything towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and anything towards Mordor,
//      go towards the Shire and ready weapon 4.
//      
//      If you hold weapon 4,
//      and there are orcs towards the Shire,
//      nothing towards the Lonely Mountain,
//      anything towards the Undying Lands,
//      and anything towards Mordor,
//      go towards the Lonely Mountain and ready weapon 3.
  )

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  EmptyBot.run()

  stage = EmptyBot.mainStage
}
