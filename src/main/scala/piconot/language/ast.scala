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
case class Go(direction: Direction) extends Action {
  override def toString: String = "go " + (direction toString)
}
case class Turn(direction: Direction) extends Action {
  override def toString: String = "turn " + (direction toString)
}


class Rule(val surroundings: Map[Direction, Boolean], val actions: Seq[Action],
    val transition: Option[Name]) {
  override def toString: String = {
    val surroundingString = surroundings map {
      case (dir, open) =>
        val letter = (dir toString) charAt 0
        val description = if (open) "open" else "closed"
        letter + description
    }
    val actionString = actions mkString " "
    val transitionString = transition map {s"transition " + _} getOrElse ""
    s"rule $surroundingString $actionString $transitionString"
  }
}

class State(val name: Name, val rules: List[Rule]) {
  override def toString: String = {
    "State \"" + name + "\"\n\t" + ((rules reverse) mkString "\n\t" + "\n")
  }
}
