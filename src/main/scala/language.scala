package piconot
import scalafx.application.JFXApp
import picolib.semantics._
/**
 * @author justisallen
 */
trait language extends JFXApp{
  //NEWS
//      Rule( 
//      State("0"), 
//      Surroundings(Anything, Anything, Open, Anything), 
//      West, 
//      State("0")
//    )
 
  val blank = Surroundings(Anything,Anything,Anything,Anything)
  val No = Surroundings(Open,Anything,Anything,Anything)
  val Nx = Surroundings(Blocked,Anything,Anything,Anything)
  val So = Surroundings(Anything,Anything,Anything,Open)
  val Sx = Surroundings(Anything,Anything,Anything,Blocked)
  val Eo = Surroundings(Anything,Open,Anything,Anything)
  val Ex = Surroundings(Anything,Blocked,Anything,Anything)
  val Wo = Surroundings(Anything,Anything,Open,Anything)
  val Wx = Surroundings(Anything,Anything,Blocked,Anything)
  val X = StayHere
  val N = North
  val S = South
  val E = East
  val W = West

  
  implicit class rule(val state: Int)(val posStatus: Surroundings*) {
    val surroundingsList = posStatus.toList
    def ->(direction: MoveDirection):Rule = ->(direction,state)
    def ->(direction: MoveDirection,newstate: Int): Rule = {
      Rule( 
        State(state.toString), 
        rule.and(surroundingsList, blank), 
        direction, 
        State(newstate.toString)
      )
    }
  }
  object rule{
    def and(surroundingsList: List[Surroundings],toReturn: Surroundings): Surroundings = (surroundingsList,toReturn) match{
      case (Nil,toReturn) => toReturn
      case (No::rest,Surroundings(_,e,w,s)) => and(rest,Surroundings(Open,e,w,s))
      case (Nx::rest,Surroundings(_,e,w,s)) => and(rest,Surroundings(Blocked,e,w,s))
      case (So::rest,Surroundings(n,e,w,_)) => and(rest,Surroundings(n,e,w,Open))
      case (Sx::rest,Surroundings(n,e,w,_)) => and(rest,Surroundings(n,e,w,Blocked))
      case (Eo::rest,Surroundings(n,_,w,s)) => and(rest,Surroundings(n,Open,w,s))
      case (Ex::rest,Surroundings(n,_,w,s)) => and(rest,Surroundings(n,Blocked,w,s))
      case (Wo::rest,Surroundings(n,e,_,s)) => and(rest,Surroundings(n,e,Open,s))
      case (Wx::rest,Surroundings(n,e,_,s)) => and(rest,Surroundings(n,e,Blocked,s))
      case _ => throw new IllegalArgumentException("Bad argument, man")   
    }
  }
}