package piconot.precious

import scala.language.implicitConversions
import java.io.File
import picolib.maze.Maze

// We looked at and borrowed from Alex Gruver and Nathan Hall's implementation from the fall 2014 class

package object lotrSemantics {
  implicit def MazeBuilder(filename: String):Maze = {
    Maze("resources" + File.separator + filename);
  }
  
  
  
  val If: Rules = new Rules
  
  
  val hold: Int = 0
  val go: Int = 0
  
  def PicoPrecious(maze: Maze, rules: Rules*) {
    
  }
}
