package piconot

/**
 * @author aputman
 */
object Empty extends PracticalBot {
  picobot("empty") (
    state ("toCorner") (
      move(left),
      move (up) (
        check(left -> blocked)
      ),
      stay (
        check(left -> blocked, up -> blocked) -> "toRight"
      )
    ),
    
    state ("toRight") (
      move (right),
      move (down) (
        check(right -> blocked) -> "toLeft"
      )
    ),
    
    state ("toLeft") (
      move (left),
      move (down) (
        check(left -> blocked) -> "toRight"
      )
    )
  )
}
