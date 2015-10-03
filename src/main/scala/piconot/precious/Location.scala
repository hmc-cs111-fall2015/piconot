package piconot.precious

package object locations {
  
  object LocationFullOf extends Enumeration {
    type fullOf = Value
    val Anything, Nothing, Orcs = Value
  }
  
  import LocationFullOf._
  
  val empty: fullOf = Nothing
  val orcs: fullOf = Orcs
  val anything: fullOf = Anything
  
  class Location(val direction: String) {
    var contents: fullOf;
    
    def is(value: fullOf): Location = { 
      contents = value 
      this
    }
    def has(value: fullOf): Location = { 
      contents = value 
      this
    }
  }
  
  object Shire extends Location(direction="North") 
  object UndyingLands extends Location(direction="West")
  object Mordor extends Location(direction="South")
  object LonelyMountain extends Location(direction="East")
}