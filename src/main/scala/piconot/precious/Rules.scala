package piconot.precious

/*
 *    When(holding weapon 1)
      and( Shire is empty).and(LonelyMountain has anything).and.(UndyingLands is empty).and( Mordor has anything)
      then(go).towards(UndyingLands).and(ready weapon 1),

 */

import locations._
import weapons._
import scala.collection.mutable._

class Rules() {
  implicit def locationAsDirection(loc: Location):String = loc.direction
  // Stored in NEWS order
  var directions: Buffer[LocationFullOf.fullOf] = new ArrayBuffer(4)
  var resultDirection: String = ""
  var initialState: Int = -1;
  var finalState: Int = -1;
  
  def If(weapon: Weapon): Rules = {
    var rule:Rules = new Rules
    rule setState weapon
    rule
  }
  
  def you(unused: Any): Rules = this  
  
  def and(place: Location): Rules = {
    directions{getLocationIndex(place.direction)} = place.contents
    this
  }
  
  def and(weapon: Weapon): Rules = {
    setState(weapon)
    this
  }
  
  def then(unused: Any): Rules = this
  
  def towards(direction: String): Rules = {
    resultDirection = direction
    this
  }
  
  private def getLocationIndex(direction: String) = {
    direction match {
      case "north" => 1
      case "east" => 2
      case "west" => 3
      case "south" => 4
    }
  }
  
  private def setState(weapon: Weapon):Unit = {
    if (weapon.initialState) {
      initialState = weapon.weaponNum
    } else {
      finalState = weapon.weaponNum
    }
  }
  
}