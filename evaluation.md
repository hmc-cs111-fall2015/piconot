# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

The first compromise we had to make was changing from "your nth weapon" to "weapon 1" because we didn't want to
implement 99 different functions to represent the different ordinal numbers.

The second compromise we had to make was making sure every line had an odd number of words so the method parameter syntax worked.

Then we had to stop separating parts of a rule with commas because otherwise they'd be treated as different arguments.

Then we had to drop "the" in front of locations and make two-word locations like "Undying Lands" into PascalCased versions "UndyingLands" because we can't have multi-word identifiers in Scala.

Then we changed "if" to "If" because `if` is a keyword.



## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 

## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?
