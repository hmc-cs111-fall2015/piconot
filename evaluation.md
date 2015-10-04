# Evaluation

## Running commentary

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

We could not implement our State syntax without more parentheses or brackets, so we changed it to `state ("StateName")` followed by the rules in braces.

We realized that the rules need to be in parentheses, and with commas separating them, because we couldn’t find a way to get the rules to be statements where multiple statements worked in a single function.

Or, alternately, we go back to our original code and add the "rule" keyword before each rule and no longer require the parentheses, braces, or commas.

We realized we needed to add a start object, otherwise the first state couldn't accept a parameter.



## On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 

## On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?
