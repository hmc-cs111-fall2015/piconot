package piconot.precious

/*
 *    When(holding weapon 1)
      and( Shire is empty).and(LonelyMountain has anything).and.(UndyingLands is empty).and( Mordor has anything)
      then(go).towards(UndyingLands).and(ready weapon 1),

 */

import picolib.semantics._
import scala.collection.mutable._

class RuleBuilder() {
  // Stored in NEWS order
  var northContents: RelativeDescription = null
  var eastContents: RelativeDescription = null
  var westContents: RelativeDescription = null
  var southContents: RelativeDescription = null
  var resultDirection: MoveDirection = null
  var initialState: State = null
  var finalState: State = null
  
  var setInitialState: Boolean = false;
  var directionToSet: Int = -1;
  
  def toRule(): Rule = {
    val surroundings: Surroundings = Surroundings(northContents,eastContents,westContents,southContents)
    Rule(initialState, surroundings, resultDirection, finalState)
  }
  
  def you(toHold: Boolean): RuleBuilder = {
      setInitialState = toHold
      this
  } 
  
  def and(place: MoveDirection): RuleBuilder = {
    place match {
      case North => directionToSet = 0
      case East => directionToSet = 1
      case West => directionToSet = 2
      case South => directionToSet = 3
    }
    this
  }
  
  def weapon(weaponNum: State): RuleBuilder = {
    if (setInitialState) {
      initialState = weaponNum;
    } else {
      finalState = weaponNum;
    }
    this
  }
  
  def and(toReady: Boolean): RuleBuilder = {
    setInitialState = toReady
    this
  }
  
  def is(contents: RelativeDescription): RuleBuilder = {
    directionToSet match {
      case 0 => northContents = contents
      case 1 => eastContents = contents
      case 2 => westContents = contents
      case 3 => southContents = contents
    }
    this
  }
  
  def has(contents: RelativeDescription): RuleBuilder = {
    is(contents)
  }
  
  def then (stay: MoveDirection): RuleBuilder = {
    resultDirection = stay
    this
  }
  def then(unused: Any): RuleBuilder = this
  
  def towards(direction: MoveDirection): RuleBuilder = {
    resultDirection = direction
    this
  }
  
}