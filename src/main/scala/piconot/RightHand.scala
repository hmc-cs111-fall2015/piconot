package piconot

/**
 * @author aputman
 */
object RightHand extends PracticalBot {
  // This is actually the LEFT hand rule :^)

  // Ideally, the maze statement would go somewhere else?
  // We might want a way for users to add keywords into their programs
  // The program relies on the user using reasonable state names for 
  // program readability
  picobot("maze") (
    // future: removing parens for the state names
    state ("facingUp") (
      move (up) ( "facingLeft" ),
      // future: else statements would help be more expressive
      // else stay ("facingRight")
      stay (
        // not sure about check syntax (ideas??)
        check (up -> blocked) -> "facingRight"
      )
    ),
    
    state ("facingRight") (
      // The commas delimiting move/stay rules can be removed... 
      // maybe with a global list that gets added to when the program runs
      move (right) ( "facingUp" ),
      stay (
        check (right -> blocked) -> "facingDown"
      )
    ),
    
    state ("facingDown") (
      move (down) ( "facingRight" ),
      stay (
        check (down -> blocked) -> "facingLeft"
      )
    ),
    
    state ("facingLeft") (
      move(left)("facingDown"),
      stay (
        check (left -> blocked) -> "facingUp"
      )
    )
  )
}