package piconot

/**
 * @author dhouck
 */
package object language {
  type Name = String
  type AST = Seq[State]
  object start extends Parser(Seq()) with wantsState
  implicit def astToPicolibRules(eos: endOfState) = eos.toRules()
  
  type Maze = picolib.maze.Maze
  final val Maze = picolib.maze.Maze
  type Picobot = picolib.semantics.Picobot
  final val Picobot = picolib.semantics.Picobot
  type GUIDisplay = picolib.semantics.GUIDisplay
  final val GUIDisplay = picolib.semantics.GUIDisplay
  type TextDisplay = picolib.semantics.TextDisplay
}