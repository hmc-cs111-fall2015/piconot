# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

First, we added a package line and put all the code in an object, because we are
pretty sure they are mandatory. We got rid of the curly braces around the state
declarations because we realized we could go without them and decided they were
not the most beautiful. We added "and" between the direction and the next state
because the parser appears to require something like "object, method, object,
method...". We added pre-declarations of the states because it was important to
us that there was a compile-time check that the states provided by the user
really existed, and we couldn't get this checking without pre-declaration. We
thought this syntax wasn't the most beautiful and we found a way with scala
macros that could make something better possible but decided it was too much
work for too little reward. Each state ends with a - sign because we needed a
function to be called. We would have preferred to use a colon, but that can't be
a function name in Scala.

## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 
3

## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?
3

The API was pretty good. We didn't like the Surroundings data structure because
we stored our "positive (blocked) directions" and "negative (unblocked)
directions" in two lists and mapping it was slightly annoying. We also didn't
like how the state of the first rule was the starting state because that was a
little confusing.
