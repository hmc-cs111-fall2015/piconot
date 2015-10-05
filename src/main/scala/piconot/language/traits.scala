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
    val contradictory = cardinalDirs exists {pair: (Direction,Boolean) =>
      (cardinalEquiv get (pair._1)) == Some(!(pair._2))
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
  
  def dirToPicolibDir(dir: Direction): lib.MoveDirection = {
    dir match {
      case North => lib.North
      case East => lib.East
      case South => lib.South
      case West => lib.West
      case _ =>
        throw new IllegalArgumentException("Tried to treat relative direction as absolute")
    }
  }
  
  def multipleDirsToRulesList(statePrefix: String, stepNumber: Int, directions: List[lib.MoveDirection], end: lib.State)
      : List[lib.Rule] = {
    val currentState = lib.State(statePrefix + stepNumber)
    val nextState =
      if ((directions length) == 1) end else lib.State(statePrefix + (stepNumber + 1))
    val anySurroundings = lib.Surroundings(lib.Anything,lib.Anything,lib.Anything,lib.Anything)
    directions match {
      case Nil => Nil
      case (dir::rest) =>
        val rule = lib.Rule(currentState, anySurroundings, dir, nextState)
        rule::(multipleDirsToRulesList(statePrefix, stepNumber + 1, rest, end))
    }
  }
  
  def ruleToPicolibRules(rule: Rule, stateName: Name, facing: Direction):List[lib.Rule] = {
    val startState = toStateName(stateName, facing)
    surrToPicolibSurr(rule.surroundings, facing) match {
      case None => List()
      case Some(surroundings) => 
        val actions = cardinalizeActions(rule.actions toList, facing)
        val Turn(finalDir) = actions last
        val endState = toStateName(rule.transition getOrElse(stateName), finalDir)
        val dirsToGo = actions.init map {case Go(dir) => dirToPicolibDir(dir)}
        dirsToGo match {
          case Nil => List(lib.Rule(lib.State(startState),
                                    surroundings,
                                    lib.StayHere,
                                    lib.State(endState)))
          case dir::Nil => List(lib.Rule(lib.State(startState),
                                         surroundings,
                                         dir,
                                         lib.State(endState)))
          case (dir::more) =>
            val statePrefix = startState + s" $surroundings Step "
            val rule = lib.Rule(lib.State(startState), surroundings, dir, lib.State(statePrefix + "2"))
            multipleDirsToRulesList(statePrefix, 2, more, lib.State(endState))
        }
    }
  }
  
  def toRule(ast: AST): List[lib.Rule] = {
    ast.reverse flatMap {state =>
      state.rules.reverse flatMap {(rule: Rule) =>
        List(North, East, South, West) flatMap {dir =>
          ruleToPicolibRules(rule, state.name, dir)
        }
      }
    } toList
  }
}

