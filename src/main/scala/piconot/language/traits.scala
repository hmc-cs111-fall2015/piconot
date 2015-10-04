package piconot.language

/**
 * @author dhouck apinson
 */

// Surroundings: Direction + open or closed
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

// The parser takes in a tree that will be changed as rules are added
class Parser(val tree: AST) {
  def currentState: State = tree head
  def currentRule: Rule = currentState.rules head
}

// all the wantsX traits handle instructions/rules/states that can 
// take in more instructions/rules/states
// wantsInstructions: allows for more instructions
trait wantsInstructions extends Parser {
  trait nextTraits extends endOfState with wantsRule
  protected def addAction(action: Action): AST = {
    val newRule = new Rule(currentRule.surroundings, 
        action +: currentRule.actions,
        currentRule.transition)
    val newState = new State(currentState.name, newRule +: (currentState.rules tail))
    newState +: (tree tail)
  }
  // go and turn add the actions, then can take in more instructions
  // or the next state
  def go(direction: Direction): nextTraits with wantsInstructions = {
    new Parser(addAction(Go(direction))) with nextTraits with wantsInstructions
  }
  def turn(direction: Direction): nextTraits with wantsInstructions = {
    new Parser(addAction(Turn(direction))) with nextTraits with wantsInstructions
  }
  def transition(name: Name): nextTraits = {
    val newRule = new Rule(currentRule.surroundings, currentRule.actions, Some(name))
    val newState = new State(currentState.name, newRule +: (currentState.rules tail))
    val newTree = newState +: (tree tail)
    new Parser(newTree) with nextTraits
  }
}

trait wantsLastRuleInstructions extends Parser {
  val thisAsWantsInstructions = new Parser(tree) with wantsInstructions
  def go(direction:Direction): endOfState with wantsInstructions = {
    thisAsWantsInstructions go direction
  }
  def turn(direction:Direction): endOfState with wantsInstructions = {
    thisAsWantsInstructions turn direction
  }
  def transition(name: Name): endOfState = {
    thisAsWantsInstructions transition name
  }
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
  def rule(surroundings: Surroundings*): wantsInstructions = {
    new Parser(addRule(surroundings)) with wantsInstructions
  }
  def rule(surroundings: Anywhere.type): wantsLastRuleInstructions = {
    val newTree = addRule(Seq())
    new Parser(newTree) with wantsLastRuleInstructions
  }
}

trait wantsState extends Parser {
  def state(name: Name) : wantsRule = {
    new Parser((new State(name, List())) +: tree) with wantsRule
  }
}

trait endOfState extends wantsState {
  import picolib.{semantics => lib}
  
  def toRules(): List[lib.Rule] = toRule(tree)
  def toStateName(name: Name, dir: Direction): String = name + " " + dir.toString
  
  def surrToPicolibSurr(surroundings: Map[Direction, Boolean], dir: Direction): 
    Option[lib.Surroundings] = {
    
    val (cardinalDirs, relativeDirs) = surroundings.partition(_._1.absolute)
    val cardinalEquiv = relativeDirs.map{p => p._1.of(dir) -> p._2}
    val contradictory = cardinalDirs forall{pair: (Direction,Boolean) => 
        (cardinalEquiv get (pair._1)) != Some(!(pair._2))
    }
    if (contradictory) None else {
      val combined = (cardinalDirs++cardinalEquiv).mapValues{if (_) lib.Open else lib.Blocked}
      
      Some(lib.Surroundings(combined.getOrElse(North, lib.Anything),
                            combined.getOrElse(East, lib.Anything),
                            combined.getOrElse(West, lib.Anything),
                            combined.getOrElse(South, lib.Anything)))
    }
  }
  
  def cardinalizeActions(ourActions: List[Action], facing: Direction): List[Action] = {
    ourActions reverse match {
      case Nil => List(Turn(facing))
      case Go(dir)::rest => 
        Go(if (dir.absolute) dir else dir of(facing))::cardinalizeActions(rest, facing)
      case Turn(dir)::rest =>
        cardinalizeActions(rest, dir of(facing))
    }
  }
  
  def ruleToPicolibRules(rule: Rule, stateName: Name, dir: Direction):List[lib.Rule] = {
    val startState = toStateName(stateName, dir)
    surrToPicolibSurr(rule.surroundings, dir) match {
      case None => List()
      case Some(surroundings) => 
        val moveDir = North//TODO
        val finalDir = North//TODO
        val endState = toStateName(rule.transition getOrElse(stateName), finalDir)
        val ruleList = List()
        ruleList
    }
  }
  
  def toRule(ast: AST) = {//TODO
    val firstState = ast head
    val rulesList = firstState.rules flatMap {(rule: Rule) =>
      List(North, East, South, West) map {ruleToPicolibRules(rule, firstState.name, _)}
    }
    List()
  }
}

