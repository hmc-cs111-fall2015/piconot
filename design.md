# Design

Each state is specified independently, with its rules in a block underneath the declaration. States are declared as
```state StateName```
where state names are arbitrary identifiers.
Rules are declared with the surroundings followed by an arrow, then a `go` instruction with a direction (`North`, ect.) and/or a `transition` instruction with a new state. 


Surroundings are represented by a list of one or more conditions, with the direction as a capital letter and specifiers "open" or "closed". If any directions are not specified, they will be assumed to be "*". For example, `Nopen` is equivalent to "x***", while `Wclosed, Sopen` is equivalent to "*x*W". The directions can be specified in any order. Finally, there is the `Any` surroundings, which is interpreted as "****" and, if other rules are declared, must be declared last.
If there are multiple non-any rules that match multiple surroundings, the first is chosen and the compiler will output a warning.


If a `go` instruction is absent, the language equates this with "X"; equivalently, the user can put the instruction `go Nowhere`. Likewise, if no new state is specified, the language equates this with "stay in the same state" (and, of course, the user can put `transition StateName`).

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

We assume that the users have basic knowledge of how Picobot operates, but not any knowledge of programming or DFAs.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?



## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?



## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?



## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?



## Is there anything you don’t like about your design?


