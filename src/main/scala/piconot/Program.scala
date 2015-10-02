package piconot

object PicoFSM extends PicoBot {

  val starting = State
  val goEast = State
  val downSweep = State
  val upSweep = State

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
