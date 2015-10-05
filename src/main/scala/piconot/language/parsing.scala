package piconot.language

import scala.language.postfixOps

/**
 * @author dhouck apinson
 */

/** Surroundings are declared by a capital char for the direction
 *  followed by "open" or "closed" */
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

/** Like a Surroundings, but for everything unspecified */
object Anywhere

/** The parser takes in an AST to be modified as the rules are parsed */
sealed class Parser(val tree: AST) {
  protected def currentState: State = tree head
  protected def currentRule: Rule = currentState.rules head
}

/** The start keyword */
object start extends Parser(Seq()) with wantsState

/** A trait for a parser where the next keyword can be an Instruction
 *  (go, turn, transition) */
sealed trait wantsInstructions extends Parser {
  protected trait nextTraits extends endOfState with wantsRule
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
    val newState = new State(currentState.name, newRule +: (currentState.rules tail))
    val newTree = newState +: (tree tail)
    new Parser(newTree) with nextTraits
  }
}

/** A trait for the parser to read the instructions for the last rule
 *  of a state */
sealed trait wantsLastRuleInstructions extends Parser {
  protected val thisAsWantsInstructions = new Parser(tree) with wantsInstructions
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

/** A trait for the parser to read the rules of a state */
sealed trait wantsRule extends Parser {
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

/** A trait for the parser to read a state */
sealed trait wantsState extends Parser {
  def state(name: Name) : wantsRule = {
    new Parser((new State(name, List())) +: tree) with wantsRule
  }
}

/** A trait indicating that we might done with the picobot program */
sealed trait endOfState extends wantsState {
  import picolib.{semantics => lib}
  
  def toRules(): List[lib.Rule] = toRule(tree)
  protected def toStateName(name: Name, dir: Direction): String = name + " " + dir.toString
  
  /** Converts the surroundings of a rule to the format expected by picolib */
  protected def surrToPicolibSurr(surroundings: Map[Direction, Boolean], dir: Direction): 
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
  
  /** Converts relative actions to cardinal actions
   *  
   *  The list of actions is converted to a list of go actions and a single turn */
  protected def cardinalizeActions(ourActions: List[Action], facing: Direction)
      : (List[Go], Turn) = {
    ourActions reverse match {
      case Nil => (Nil, Turn(facing))
      case Go(dir)::rest =>
        val (restActions, turnDir) = cardinalizeActions(rest, facing)
        (Go(if (dir.absolute) dir else dir of(facing))::restActions, turnDir)
      case Turn(dir)::rest =>
        cardinalizeActions(rest, dir of(facing))
    }
  }
  /** Converts Direction to a picolib direction */
  protected def dirToPicolibDir(dir: Direction): lib.MoveDirection = {
    dir match {
      case North => lib.North
      case East => lib.East
      case South => lib.South
      case West => lib.West
      case _ =>
        throw new IllegalArgumentException("Tried to treat relative direction as absolute")
    }
  }
  
  /** Given a list of directions, creates a list of picolib rules to go in
   *  each direction in sequence */
  protected def multipleDirsToRulesList(statePrefix: String, stepNumber: Int, directions: List[lib.MoveDirection], end: lib.State)
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
  /** Converts a Rule to an equivalent sequence of picolib rules */
  protected def ruleToPicolibRules(rule: Rule, stateName: Name, facing: Direction):List[lib.Rule] = {
    val startState = toStateName(stateName, facing)
    surrToPicolibSurr(rule.surroundings, facing) match {
      case None => List()
      case Some(surroundings) => 
        val (actions, Turn(finalDir)) = cardinalizeActions(rule.actions toList, facing)
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
  
  /** Converts an AST to a list of picolib rules */
  protected def toRule(ast: AST): List[lib.Rule] = {
    ast.reverse flatMap {state =>
      state.rules.reverse flatMap {(rule: Rule) =>
        List(North, East, South, West) flatMap {dir =>
          ruleToPicolibRules(rule, state.name, dir)
        }
      }
    } toList
  }
}

