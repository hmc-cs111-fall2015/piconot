# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

We got into the IDE and immediately realized that things were going to be harder than we thought they would be. We  went through various discussions of what would be objects,classes, and operators and eventually settled on a compromise of where to start. We changed to:

'rule( # seq ) -> action' which is pretty different but we're planning to slim down significantly by removing the need for the rule keyword.

Yikes. We're figuring out how we want to pass our arguments to rule and so far the only way we can see to do that is as a list. This isn't ideal but hopefully we'll be able to make it better. End day 1

We realized that using a list would require users to use nested parentheses when writing the syntax, so we decided to switch to `rule` being a class whose constructor accepts an int representing the state, followed by *args of position statuses (i.e., whether the north wall is open or blocked, etc.).

We remembered currying! wow! this changes everything.

Wow this is super cool. We are feeling significantly less scared and significantly more excited about this lab and also about scala. Robin especially thinks that scala is awesome.

We have come a long way about our compromises. At first we were hoping to compromise not very much and then about 1.5 hours in we were thinking that everything would have to be completely compromised and now were striking a balance!

We're done, yay! Now that we have hindsight we think that it still feels pretty scala-y which is kind of a bummer. This is mostly becuse we have to pass our rules as a list and also the parentheses really suck. Also all the imports and stuff at the bottom is kind of annoying but we think that if we were to render it as it is rendered in picobot that we could hide a lot of that stuff.

This was pretty fun. We're pretty happy moving on to something else but if other people want to keep working on this we're happy to.

## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 
4 or 5 out of 10
the rule being the biggest deviation and then the parentheses

## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?
The api wasnt really the hard part for us we dont think. Maybe 3 or 4 out of ten. We think if we were to do the final "ten percent" it would be a lot harder maybe a 5-7 range
