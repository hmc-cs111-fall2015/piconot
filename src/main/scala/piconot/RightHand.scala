package piconot

object RightHand extends PicoBot {

  use map "resources/maze.txt"

  val faceN, faceE, faceW, faceS = States

  faceN-
  -N -> N and faceE
  +N -> faceW

  faceE-
  -E -> E and faceS
  +E -> faceN

  faceS-
  -S -> S and faceW
  +S -> faceE

  faceW-
  -W -> W and faceN
  +W -> faceS
}
