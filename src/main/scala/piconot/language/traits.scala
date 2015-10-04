package piconot.language

/**
 * @author dhouck apinson
 */

sealed class Surroundings(val direction: Direction, val open: Boolean)
case object Nopen extends Surroundings(North, true)
case object Eopen extends Surroundings(East, true)
case object Wopen extends Surroundings(West, true)
case object Sopen extends Surroundings(South, true)
case object Nclosed extends Surroundings(North, false)
case object Eclosed extends Surroundings(East, false)
case object Wclosed extends Surroundings(West, false)
case object Sclosed extends Surroundings(South, false)
case object Fopen extends Surroundings(Forward, true)
case object Bopen extends Surroundings(Back, true)
case object Lopen extends Surroundings(Left, true)
case object Ropen extends Surroundings(Right, true)
case object Fclosed extends Surroundings(Forward, false)
case object Bclosed extends Surroundings(Back, false)
case object Lclosed extends Surroundings(Left, false)
case object Rclosed extends Surroundings(Right, false)

// Not actually a Surroundings
object Anywhere

class Parser(val tree: AST) {
  def currentState: State = tree head
  def currentRule: Rule = currentState.rules head
}

abstract trait wantsInstructions extends Parser {
  trait nextTraits
  protected def addAction(action: Action): AST = {
    val newRule = new Rule(currentRule.surroundings, 
        action +: currentRule.actions,
        currentRule.transition)
    val newState = new State(currentState.name, newRule +: (currentState.rules tail))
    newState +: (tree tail)
  }
  def go(direction: Direction): nextTraits with wantsInstructions = {
    new Parser(addAction(Go(direction))) with nextTraits with wantsInstructions
  }
  def turn(direction: Direction): nextTraits with wantsInstructions = {
    new Parser(addAction(Turn(direction))) with nextTraits with wantsInstructions
  }
  def transition(name: Name): nextTraits = {
    val newRule = new Rule(currentRule.surroundings, currentRule.actions, Some(name))
    val newState = new State(currentState.name, currentState.rules)
    val newTree = newState +: (tree tail)
    new Parser(newTree) with nextTraits
  }
}

trait wantsLastRuleInstructions extends wantsInstructions {
  trait nextTraits extends wantsState
}

trait wantsNonlastRuleInstructions extends wantsInstructions {
  trait nextTraits extends wantsState with wantsRule
}

trait wantsRule extends Parser {
  /**
   * Add a Rule with the given surroundings to the current state, returning the
   * new AST
   */
  protected def addRule(surroundings: Seq[Surroundings]): AST = {
    val kvps = surroundings map {(x:Surroundings) => x.direction -> x.open}
    val map: Map[Direction, Boolean] = Map(kvps: _*)
    val newRule = new Rule(map, Seq(), None)
    val newState = new State(currentState.name, newRule +: currentState.rules)
    newState +: (tree tail)
  } 
  def rule(surroundings: Surroundings*): wantsLastRuleInstructions = {
    new Parser(addRule(surroundings)) with wantsLastRuleInstructions
  }
  def rule(surroundings: Anywhere.type): wantsNonlastRuleInstructions = {
    val newTree = addRule(Seq())
    new Parser(newTree) with wantsNonlastRuleInstructions
  }
}

trait wantsState extends Parser {
  def state(name: Name) : wantsRule = {
    new Parser((new State(name, List())) +: tree) with wantsRule
  }
}

