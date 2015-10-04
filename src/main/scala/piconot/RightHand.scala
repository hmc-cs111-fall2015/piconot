package piconot

/**
 * @author aputman
 */
object RightHand extends PracticalBot {
  // This is actually the LEFT hand rule :)
  picobot("maze") (
    state ("facingUp") (
      move (up) ( "facingLeft" ),
      stay (
        check (up -> blocked) -> "facingRight"
      )
    ),
    
    state ("facingRight") (
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