package piconot.language

/**
 * @author dhouck apinson
 */

// We couldn’t get Enumeration to work
abstract sealed class Direction(val absolute: Boolean)
case object North extends Direction(true)
case object East extends Direction(true)
case object South extends Direction(true)
case object West extends Direction(true)
case object Forward extends Direction(false)
case object Back extends Direction(false)
case object Left extends Direction(false)
case object Right extends Direction(false)

abstract sealed class Action
case class Go(direction: Direction) extends Action
case class Turn(direction: Direction) extends Action


class Rule(val surroundings: Map[Direction, Boolean], val actions: Seq[Action],
    val transition: Option[Name])

class State(val name: Name, val rules: List[Rule])
