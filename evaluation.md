# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

The first compromise we had to make was changing from "your nth weapon" to "weapon 1" because we didn't want to
implement 99 different functions to represent the different ordinal numbers.

The second compromise we had to make was making sure every rule had an odd number of words - this is because for the programmer to be able to write their code without dots and parentheses, every method needed to take 1 argument. Including the `If` in the front, this meant that every rule had to have odd length.

We had to change `and there is` in the first clause of the rule to just `and` so that we could make and a function that took a `<contents>`.

Then we had to stop separating parts of a rule with commas because otherwise they'd be treated as different arguments. We also had to change the separator between rules from periods to commas so that they would be interpreted as separate arguments.

Then we had to drop "the" in front of locations and make two-word locations like "Undying Lands" into PascalCased versions "UndyingLands" because we can't have multi-word identifiers in Scala.

Then we changed "if" to "If" because `if` is a keyword.

Then we had to drop "are holding weapon 1" for "hold weapon 1" because it didn't parse "holding weapon 1" the way we wanted it
to.  It interpreted "holding" as the argument to the previous method, while holding was an object, weapon was a method on that
object, and 1 was the argument to that method.  We refactored to get rid of that and make everything a method of `RuleBuilder`
or a constant.

Then we added "stay" because we forgot about it.

Then we described a location as `and <location> has/is <contents>` instead of `<contents> towards <location>` to simplify implementation.



## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 
7


## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?
4