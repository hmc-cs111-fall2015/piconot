package piconot

/**
 * @author dhouck apinson
 */

package object language {
  /** The name of a state */
  type Name = String
  /** The abstract syntax tree for our language */
  type AST = Seq[State]
  /** The start keyword */
  object start extends Parser(Seq()) with wantsState
  /** Converts the AST to a list of picolib rules */
  implicit def astToPicolibRules(eos: endOfState) = eos.toRules()
  
  /** Import things needed for the client from picolib.semantics */
  type Maze = picolib.maze.Maze
  final val Maze = picolib.maze.Maze
  type Picobot = picolib.semantics.Picobot
  final val Picobot = picolib.semantics.Picobot
  type GUIDisplay = picolib.semantics.GUIDisplay
  final val GUIDisplay = picolib.semantics.GUIDisplay
  type TextDisplay = picolib.semantics.TextDisplay
}