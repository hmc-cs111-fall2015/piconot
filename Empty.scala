package piconot

object Empty extends PicoBot {

  use map "resources/empty.txt"

  val starting, goEast, downSweep, upSweep = States

  starting-
  -N -> N and starting
  +N -> goEast

  goEast-
  -E +N -> E and goEast
  +E +N -> downSweep

  downSweep-
  -S -> S and downSweep
  +S -> W and upSweep

  upSweep-
  -N -> N and upSweep
  +N -> W and downSweep
}
