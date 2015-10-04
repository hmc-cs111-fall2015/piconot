# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

To declare a state, we have to pass the string as an argument to the state function. We made this change to keep our implementation time from exploding. 

To declare a rule, the user has to call check. We had to make this change to fit the restrictions of Scala without doing anything too crazy.

For now, we'll write it so that the extra statements at the bottom can stay, for ease.
"
val maze = Maze("resources" + File.separator + "empty.txt")
object RuleBot extends Picobot(maze, rules)
    with TextDisplay with GUIDisplay
RuleBot.run()
stage = RuleBot.mainStage
"
This doesn't look like it should be part of the DSL.

We had some design ideas about implied statements such as "go North" implies North is empty and not declaring a next state would be identical to stating the current state. So if a user does not declare that he wants to go to State 0 from State 0, then we infer that the user wants to go to State 0. We may include these changes at the end if we have time. We just wanted to get a MVP before adding these. 

Instead of rules seperated by curly braces, we implemented the states as a Sequence of Sequences of Rules so they have to be delineated by soft parens instead of curly braces as we originally wanted.
Also, to generate a list, we needed to add commas after each rule.
The alternative would be too cumbersome to build. Also, in designing the implmentation we had forgotten that multiple arguments could not be passed in curly braces.

We originally designed states that included control flow (else statements) so that rules could have precedence and ordering. We removed this part of the syntax for now because it was too difficult. We may be able to include else statements later on.

We were more comfortable with designing function, so some of our language features were inaccessible without switching to a class design with applies. With more time for implementation, we will probably try to switch over to classes...

We were able to go back to the changes we wanted to do. Both implied statements work, sort-of. Instead of not placing a gotoState after a move statement, the user has to put "()" due to the function design.

Just kidding, we were able to get rid of "()" by using a function with an apply and an implicit conversion to add the "()". 

We were able to move the lines at the end of that class!

By changing some of the functions to classes we were able to start implementing the else's that we wanted from before. 



## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 

About 6 - we had many changed but the overall structure is similar. We did have to force the user to include more syntax-required characters.

## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?

We made a number of changes to our ideal syntax to keep our implementation within 4-5 hours. Given that, the overall difficulty of mapping our syntax to the API was 3-4.