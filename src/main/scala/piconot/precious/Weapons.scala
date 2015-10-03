package piconot.precious

package object weapons {  
  class Weapon(val initialState: Boolean) {
    var weaponNum: Int;
    def weapon(num: Int): Weapon = {
      // There can be up to 100 states, 1-100
      require(num <= 100 && num > 0)      
      // Picobot expects states from 0-99
      weaponNum = num - 1
      this
    }
  }
  
  object hold extends Weapon(initialState=true)
  object ready extends Weapon(initialState=false)
}