package piconot

object Empty extends PicoBot {

  use map "resources/empty.txt"

  val starting, goEast, downSweep, upSweep = States

  starting-
  -N -> N
  +N -> goEast

  goEast-
  -E +N -> E
  +E +N -> downSweep

  downSweep-
  -S -> S
  +S -> W and upSweep

  upSweep-
  -N -> N
  +N -> W and downSweep
}
