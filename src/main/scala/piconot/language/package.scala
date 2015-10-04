package piconot

/**
 * @author dhouck
 */
package object language {
  type Name = String
  type AST = Seq[State]
  object start extends Parser(Seq()) with wantsState
}